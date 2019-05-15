package display;

import biuoop.DrawSurface;
/**
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d a draw surface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    void timePassed(double dt);
}
