package operators;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;

/**
 * in charge of translate text into a map of qualities.
 */
public class BlocksDefinitionReader {

    /**
     * in charge of translate text into a map of qualities.
     * @param def an array of strings
     * @return a map of qualities.
     */
    public static Map<String, Object> definitionsMap(String[] def) {
        Map<String, Object> definitions = new TreeMap<>();
        ColorsParser colorsParser = new ColorsParser();

        for (String s: def) {
            String[] temp = s.split(":");
            if (temp[0].equals("symbol")) {
                definitions.put(temp[0], temp[1]);
            } else if (temp[0].equals("width")) {
                definitions.put(temp[0], Integer.parseInt(temp[1]));
            } else if (temp[0].equals("height")) {
                definitions.put(temp[0], Integer.parseInt(temp[1]));
            } else if (temp[0].equals("hit_points")) {
                definitions.put(temp[0], Integer.parseInt(temp[1]));
            } else if (temp[0].startsWith("fill") || temp[0].equals("stroke")) {
                //the color itself or image path
                String fill = temp[1].substring(temp[1].indexOf("(") + 1, temp[1].length() - 1);
                //the parse 'color' or 'image'
                String type = temp[1].substring(temp[1].indexOf(":") + 1, temp[1].indexOf("("));
                if (type.equals("image")) {
                    try {
                        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fill);
                        Image img = ImageIO.read(inputStream);
                        definitions.put(temp[0] + "Img", img);
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                } else if (fill.startsWith("RGB")) {
                    Color color = colorsParser.rgbColorFromString(fill);
                    definitions.put(temp[0] + "Color", color);
                } else {
                    Color color = colorsParser.awtColorFromString(fill);
                    definitions.put(temp[0] + "Color", color);
                }
            }
        }
        return definitions;
    }

    /**
     * the code in BlocksDefinitionReader will create the appropriate BlockCreator implementations
     * according to the definitions in the bdef lines, and populate the BlocksFromSymbolsFactory with
     * the BlockCreators and their associated symbols.
     * @param reader a reader
     * @return BlocksFromSymbolsFactory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Integer> spacers = new TreeMap<>();
        Map<String, Object> defaultDef = new TreeMap<>();
        List<Map<String, Object>> types = new LinkedList<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (!line.equals("") && !line.startsWith("#")) {
                    String[] def = line.substring(line.indexOf(" ") + 1).split(" ");
                    if (line.startsWith("default")) {
                        defaultDef.putAll(definitionsMap(def));
                    } else if (line.startsWith("bdef")) {
                        types.add(definitionsMap(def));
                    } else if (line.startsWith("sdef")) {
                        spacers.put(def[0].substring(def[0].indexOf(":") + 1),
                                Integer.parseInt(def[1].substring(def[1].indexOf(":") + 1)));
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        //a map will specified the appropriate BlockCreator for each block type
        Map<String, BlockCreator> creators = new TreeMap<>();
        for (Map<String, Object> definitions: types) {
            creators.put((String) definitions.get("symbol"), new Creator(definitions, defaultDef));
        }
        //build a blocksFactory in accordance to the lists and maps above
        return new BlocksFromSymbolsFactory(spacers, creators);
    }
}