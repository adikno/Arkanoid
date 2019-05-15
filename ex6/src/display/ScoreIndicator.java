package display;

import biuoop.DrawSurface;
import game.GameLevel;
import operators.Counter;

import java.awt.Color;

/**
 * In charge of representing the score on the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * A constructor.
     * @param score the current score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        String s = "Score: " + String.valueOf(this.score.getValue());
        d.drawText(350, 20, s, 18);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }

    /**
     * Adding the indicator to the game.
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}
