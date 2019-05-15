package display;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import instruments.BackGround;
import instruments.Block;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * in charge of displaying the high scores.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * a constructor.
     * @param scores a high-scores table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * Draw sprites on the surface and more.
     *
     * @param d  draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        List<Sprite> sprites = new LinkedList<>();
        Block block = new Block(new geometry.Rectangle(new Point(0, 0), 800,
                600), Color.GRAY);
        Line line = new Line(new Point(100, d.getHeight() / 4 + 5), new Point(400, d.getHeight() / 4 + 5),
                Color.GREEN);
        sprites.add(block);
        sprites.add(line);
        BackGround backGround = new BackGround(sprites);
        backGround.drawOn(d);
        d.setColor(Color.YELLOW);
        d.drawText(40, 40 , "High Scores", 35);
        d.setColor(Color.GREEN);
        d.drawText(100, d.getHeight() / 4, "Player Name", 30);
        d.drawText(300, d.getHeight() / 4, "Score", 30);
        d.setColor(Color.orange);
        int i = d.getHeight() / 4 + 40;
        for (ScoreInfo s: this.scores.getHighScores()) {
            d.drawText(100, i, s.getName(), 25);
            d.drawText(300, i, String.valueOf(s.getScore()), 25);
            i += 30;
        }
        d.setColor(Color.black);
        d.drawText(240, d.getHeight() / 4 * 3, "Press space to continue", 32);
    }

    /**
     * Return false if the game should keep running and true otherwise.
     *
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() {
        return false;
    }

    /**
     * prepare the boolean members for reuse.
     */
    public void setShouldStop() {
    }
}
