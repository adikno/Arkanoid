package operators;

import game.LevelInformation;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * in charge of making map of level sets and their level-information.
 **/
public class LevelSetsReader {

    /**
     * in charge of splitting level sets.
     * @param reader a reader
     * @return a list of level sets
     */
    public List<String> splitSets(java.io.Reader reader) {
        Pattern patt = Pattern.compile(".*txt");

        List<String> sets = new LinkedList<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String temp = "";
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = patt.matcher(line);
                temp = temp + ";" + line;
                if (matcher.matches()) {
                    sets.add(temp);
                    temp = "";
                }
            }
        } catch (Exception e) {
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
        return sets;
    }

    /**
     * in charge of making map of level sets and their level-informations.
     * @param reader a reader
     * @return a map of level sets and their level-informations
     */
    public Map<String, List<LevelInformation>> fromReader(java.io.Reader reader) {
        Map<String, List<LevelInformation>> map = new TreeMap<>();
        List<String> levelSets = this.splitSets(reader);
        LevelSpecificationReader levelsReader = new LevelSpecificationReader();
        InputStream inputStream;
        for (String set: levelSets) {
            String[] split = set.split(";");
            try {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(split[2]);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                map.put(split[1], levelsReader.fromReader(reader));
            } catch (Exception e) {
                System.out.println("failed reading file");
            }
        }
        return map;
    }
}
