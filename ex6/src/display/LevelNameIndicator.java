package display;

import biuoop.DrawSurface;
import game.GameLevel;

import java.awt.Color;

/**
 * In charge of representing the level-name on the screen.
 */
public class LevelNameIndicator implements Sprite {
    private String name;

    /**
     * A constructor.
     * @param name the name of the level
     */
    public LevelNameIndicator(String name) {
        this.name = name;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        String s = "Level Name: " + String.valueOf(this.name);
        d.drawText(470, 20, s, 18);
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
