package display;

import geometry.Point;
import geometry.Rectangle;
import instruments.BackGround;
import instruments.Block;
import biuoop.DrawSurface;
import operators.Counter;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * In charge of representing the final score of the game as well as a message if the game has been completed or not.
 */
public class EndScreen implements Animation {
    private int score;
    private int lives;

    /**
     * A constructor.
     * @param score the final score
     * @param livesCounter the current number of lives
     */
    public EndScreen(Counter score, Counter livesCounter) {
        this.score = score.getValue();
        this.lives = livesCounter.getValue();
    }

    /**
     * Drawing the final score of the game as well as a message if the game has been completed or not.
     * Do that until the user presses space-key.
     * @param d draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        List<Sprite> sprites = new LinkedList<>();
        Block block = new Block(new Rectangle(new Point(0, 0), 800, 600),
                Color.GRAY);
        sprites.add(block);
        BackGround backGround = new BackGround(sprites);
        backGround.drawOn(d);
        if (this.lives == 0) {
            d.setColor(Color.black);
            d.drawText(200, d.getHeight() / 3, "Game Over.", 80);
            d.setColor(Color.red);
            d.drawText(205, d.getHeight() / 3 + 5, "Game Over.", 80);
        } else {
            d.setColor(Color.black);
            d.drawText(240, d.getHeight() / 3, "You Win!", 80);
            d.setColor(Color.blue);
            d.drawText(245, d.getHeight() / 3 + 5, "You Win!", 80);
        }
        d.setColor(Color.black);
        d.drawText(260, d.getHeight() / 4 * 3, "Your score is " + String.valueOf(this.score), 32);
    }

    /**
     * Return false if the game should keep running and true otherwise.
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() { return false; }

    /**
     * Prepare boolean members for reuse.
     */
    public void setShouldStop() {

    }
}
