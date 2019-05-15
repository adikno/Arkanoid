package display;

import biuoop.DrawSurface;
import game.GameLevel;
import operators.Counter;

import java.awt.Color;

/**
 * In charge of representing the number of lives on the screen.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * A constructor.
     * @param lives the current number of lives.
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        String s = "Lives: " + String.valueOf(this.lives.getValue());
        d.drawText(50, 20, s, 18);
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
