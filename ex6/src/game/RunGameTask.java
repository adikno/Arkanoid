package game;

import biuoop.KeyboardSensor;
import display.AnimationRunner;
import display.Task;
import display.KeyPressStoppableAnimation;
import display.EndScreen;
import operators.Counter;

import java.util.List;

/**
 * in charge of running a level-set.
 */
public class RunGameTask implements Task<Void> {
    private AnimationRunner runner;
    private List<LevelInformation> levels;
    private KeyboardSensor keyboard;
    private Counter score;

    /**
     * a constructor.
     * @param runner an animation runner
     * @param levels the levels of the set
     * @param keyboard a keyboard sensor
     * @param score a score counter
     */
    public RunGameTask(AnimationRunner runner, List<LevelInformation> levels, KeyboardSensor keyboard, Counter score) {
        this.levels = levels;
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = score;
    }

    /**
     * In charge of run the levels.
     * @return null as return value
     */
    public Void run() {
        Counter livesCounter = new Counter(7);

        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.runner, this.keyboard, livesCounter, score);

            level.initialize();
            while (livesCounter.getValue() > 0 && level.getRemainingBlocks() > 0) {
                level.playOneTurn();
            }

            if (livesCounter.getValue() == 0) {
                level.removeAllBlocks();
                break;
            }
        }
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                KeyboardSensor.SPACE_KEY, new EndScreen(this.score, livesCounter)));
        return null;
    }
}
