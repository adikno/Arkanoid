package display;

import biuoop.DialogManager;
import biuoop.GUI;
import operators.Counter;

import java.io.File;
import java.io.IOException;

/**
 * In charge of run the sub-menu.
 */
public class ShowSubMenuTask implements Task<Void> {

    private AnimationRunner runner;
    private Menu<Task<Void>>  subMenu;
    private File filename;
    private HighScoresTable scores;
    private GUI gui;
    private Counter score;

    /**
     * A constructor.
     * @param runner Animation runner
     * @param subMenu an Animation
     * @param filename the name of the high-scores file
     * @param scores an high-scores table
     * @param gui a gui
     * @param score a score counter
     */
    public ShowSubMenuTask(AnimationRunner runner, Menu<Task<Void>> subMenu, File filename,
                           HighScoresTable scores, GUI gui, Counter score) {
        this.runner = runner;
        this.subMenu = subMenu;
        this.filename = filename;
        this.scores = scores;
        this.gui = gui;
        this.score = score;
    }

    /**
     * In charge of run the sub-menu.
     * @return null as return value
     */
    public Void run() {
        this.runner.run(this.subMenu);
        Task<Void> task = subMenu.getStatus();
        task.run();
        if (scores.getRank(score.getValue()) <= scores.size()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            scores.add(new ScoreInfo(name, score.getValue()));
            try {
                scores.save(filename);
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        score.reset();
        return null;
    }
}
