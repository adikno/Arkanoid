import biuoop.GUI;
import biuoop.KeyboardSensor;
import display.HighScoresTable;
import display.AnimationRunner;
import display.Task;
import display.ShowSubMenuTask;
import display.KeyPressStoppableAnimation;
import display.Menu;
import display.ShowHiScoresTask;
import display.SubMenuAnimation;
import display.MenuAnimation;
import display.HighScoresAnimation;
import operators.Counter;
import operators.LevelSetsReader;
import game.LevelInformation;
import game.RunGameTask;

import java.io.File;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.TreeMap;

/**
 * the main class.
 */
public class Ass6Game {
    /**
     * a default WIDTH for a screen.
     */
    public static final int WIDTH_SCREEN = 800;
    /**
     * a default height for a screen.
     */
    public static final int HEIGHT_SCREEN = 600;

    /**
     * the main method.
     * @param args an array of strings.
     */
    public static void main(String[] args) {

        //search for high scores table
        File filename = new File("highscores.txt");
        HighScoresTable scores = HighScoresTable.loadFromFile(filename);

        Map<String, List<LevelInformation>> levelSets = new TreeMap<>();
        LevelSetsReader setsReader = new LevelSetsReader();
        InputStream inputStream = null;
        //search for level-sets file and make level-Information list out of it.
        try {
            if (args.length != 0) {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
            } else {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("level_sets.txt");
            }
            if (inputStream == null) {
                System.out.println("!!!!!!!!!");
            }
            File file = new File("level_sets.txt");
            /*if (!file.exists()) {
                System.out.println("!!!!!!!!");
            }*/
            levelSets = setsReader.fromReader(new InputStreamReader(inputStream));
        } catch (Exception e) {
            System.out.println("bad args or a missing file.");
        }

        GUI gui = new GUI("Arkanoid", WIDTH_SCREEN, HEIGHT_SCREEN);
        KeyboardSensor sensor = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        /**
         * an anonymous class for exit-Task.
         */
        Task<Void> exitTask = new Task<Void>() {
            /**
             * in charg of execute the task.
             *
             * @return null
             */
            public Void run() {
                System.exit(0);
                return null;
            }
        };

        Counter score = new Counter();

        Task<Void> hiScoresTask = new ShowHiScoresTask(runner,
                new KeyPressStoppableAnimation(sensor, KeyboardSensor.SPACE_KEY, new HighScoresAnimation(scores)));

        Menu<Task<Void>> menu = new MenuAnimation(sensor);
        Menu<Task<Void>> subMenu = new SubMenuAnimation(sensor);

        //for each level-set - adding a selection for the sub-menu
        Set keyset = levelSets.keySet();
        for (Object key : keyset) {
            Task<Void> runGameTask = new RunGameTask(runner, levelSets.get(key), sensor, score);
            String[] temp = ((String) key).split(":");
            subMenu.addSelection(temp[0], temp[1], runGameTask);
        }

        Task<Void> subMenuTask = new ShowSubMenuTask(runner, subMenu, filename, scores, gui, score);
        menu.addSelection("s", "Start game", subMenuTask);
        menu.addSelection("h", "High Scores", hiScoresTask);
        menu.addSelection("q", "Quit", exitTask);

        while (true) {
            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            if (task != hiScoresTask) {
                task.run();
            }
            hiScoresTask.run();
        }
    }
}
