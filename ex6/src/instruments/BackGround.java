package instruments;

import biuoop.DrawSurface;
import display.Sprite;

import java.util.List;

/**
 * Background of a level.
 */
public class BackGround implements Sprite {
    private List<Sprite> list;

    /**
     * A constructor of BackGround class.
     * @param list of sprites.
     */
    public BackGround(List<Sprite> list) {
        this.list = list;
    }
    /**
     * draw all the sprites to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        for (Sprite s: this.list) {
            s.drawOn(d);
        }
    }

    /**
     * notify all the sprites that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
    }
}
