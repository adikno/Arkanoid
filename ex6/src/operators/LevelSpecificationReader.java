package operators;

import game.Level;
import game.LevelInformation;
import instruments.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * in charge of reading files and making level-Informations accordingly.
 */
public class LevelSpecificationReader {

    /**
     * a magic string for cutting a level.
     */
    public static final String END_WORD = "END_LEVEL";
    /**
     * a default size for an array.
     */
    public static final int DEFAULT = 5;

    /**
     * in charge of splitting the text to different levels.
     * @param reader a reader
     * @return list of strings holding level info
     */
    public List<String> splitLevels(java.io.Reader reader) {
        List<String> levels = new LinkedList<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String temp = "";
            String line = bufferedReader.readLine();
            while (line != null) {
                temp = temp + ";" + line;
                // moving to the next node of the list when the current input is 'END_LEVEL'
                if (line.equals(END_WORD)) {
                    levels.add(temp);
                    temp = "";
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
        return levels;
    }

    /**
     * In charge of making a single level-informations object out of text.
     * Do it by recognize qualities of a level in the text.
     * @param text the text
     * @return level information object
     */
    public LevelInformation decipherLevel(String text) {
        String[] parts = text.split(";");
        String levelName = "";
        int[] velocities = new int[DEFAULT];
        int[] angles = new int[DEFAULT];
        Image img = null;
        int paddleSpeed = 0;
        int paddleWidth = 0;
        ColorsParser colorsParser = new ColorsParser();
        Color color = null;
        BlocksFromSymbolsFactory blocksFactory = null;
        int blocksStartX = 0;
        int blocksStartY = 0;
        int rowHeight = 0;
        int numBlocks = 0;
        boolean isBlockLayout = false;
        List<String> blockLayout = new LinkedList<>();
        try {
            for (String part : parts) {
                if (part.startsWith("level_name")) {
                    levelName = part.substring(part.indexOf(":") + 1);
                } else if (part.startsWith("ball_velocities")) {
                    String temp = part.substring(part.indexOf(":") + 1);
                    String[] some = temp.split(" ");
                    velocities = new int[some.length];
                    angles = new int[some.length];
                    for (int i = 0; i < some.length; i++) {
                        String[] split = some[i].split(",");
                        velocities[i] = Integer.parseInt(split[1]);
                        angles[i] = Integer.parseInt(split[0]);
                    }
                } else if (part.startsWith("background")) {
                        String fill = part.substring(part.indexOf("(") + 1, part.length() - 1);
                        String temp = part.substring(part.indexOf(":") + 1, part.indexOf("("));
                        if (temp.equals("image")) {
                            try {
                                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fill);
                                img = ImageIO.read(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace(System.err);
                            }
                        } else if (fill.startsWith("RGB")) {
                            color = colorsParser.rgbColorFromString(fill);
                        } else {
                            color = colorsParser.awtColorFromString(fill);
                        }
                } else if (part.startsWith("paddle_speed")) {
                    paddleSpeed = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("paddle_width")) {
                    paddleWidth = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("block_definitions")) {
                    String temp = part.substring(part.indexOf(":") + 1);
                    //File levelsFile = new File(temp);
                    InputStream inputStream = null;
                    try {
                        inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(temp);
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                    //building a blockFactory out of the block definitions
                    blocksFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(inputStream));
                } else if (part.startsWith("blocks_start_x")) {
                    blocksStartX = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("blocks_start_y")) {
                    blocksStartY = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("row_height")) {
                    rowHeight = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("num_blocks")) {
                    numBlocks = Integer.parseInt(part.substring(part.indexOf(":") + 1));
                } else if (part.startsWith("START_BLOCKS")) {
                    isBlockLayout = true;
                } else if (isBlockLayout) {
                    blockLayout.add(part);
                }
                if (part.startsWith("END_BLOCKS")) {
                    blockLayout.remove(part);
                    isBlockLayout = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Some of the definitions are missing or incompatible.");
            System.exit(0);
        }
        //sending the part that deals with the blocks to a method that will make a list of blocks out of them
        // using the blocks-Factory.
        blocksFactory.addDefinitions(blocksStartX, blocksStartY, rowHeight);
        List<Block> blocks = decipherBlocks(blockLayout, blocksFactory);
        //building a level information in accordance to all the qualities above.
        Level level = new Level(levelName, velocities, angles, paddleSpeed, paddleWidth, blocks, numBlocks);
        if (img != null) {
            level.setBackGroundImg(img);
        } else {
            level.setBackGroundColor(color);
        }
        return level;
    }

    /**
     * in charge of making a list of blocks out of text using a blocks-Factory.
     * @param blockLayout a text represents the layout of the blocks.
     * @param factory a class that know how to create blocks
     * @return list of blocks
     */
    public List<Block> decipherBlocks(List<String> blockLayout, BlocksFromSymbolsFactory factory) {
        List<Block> blocks = new LinkedList<>();
        for (String row: blockLayout) {
            char[] parts = row.toCharArray();
            for (char c: parts) {
                //if it's a spacer - update 'x' value.
                //otherwise - build a new block from a suitable type and update 'x'
                if (factory.isSpaceSymbol(c + "")) {
                    factory.setCurrX(factory.getSpaceWidth(c + ""));
                } else if (factory.isBlockSymbol(c + "")) {
                    Block block = factory.getBlock(c + "", factory.getCurrX(), factory.getCurrY());
                    blocks.add(block);
                    factory.setCurrX((int) block.getWidth());
                }
            }
            //update 'x' and 'y' at the end of each raw of the text
            factory.setCurrY();
            factory.resetCurrX();
        }
        return blocks;
    }

    /**
     * in charge of making list of level-Informations out of text.
     * @param reader a reader
     * @return list of level-Informations
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<String> levelText = this.splitLevels(reader);
        List<LevelInformation> levels = new LinkedList<>();
        for (String text: levelText) {
            levels.add(this.decipherLevel(text));
        }
        return levels;
    }
}
