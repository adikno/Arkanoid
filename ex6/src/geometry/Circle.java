package geometry;

import biuoop.DrawSurface;
import display.Sprite;

import java.awt.Color;

/**Circle is a geometric shape.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Circle implements Sprite {
    private Point location;
    private int size;
    private Color color;
    private boolean isFilled;

    /**
     * A constructor of the class Circle.
     * @param location the center of the circle
     * @param size the size of the circle
     * @param color the color of the circle
     * @param isFilled a bool determines whether the circle is filled or not
     */
    public Circle(Point location, int size, Color color, Boolean isFilled) {
        this.color = color;
        this.location = location;
        this.size = size;
        this.isFilled = isFilled;
    }
    /**
     * draw the sprite to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        if (!this.isFilled) {
            d.drawCircle((int) this.location.getX(), (int) this.location.getY(), this.size);
        } else {
            d.fillCircle((int) this.location.getX(), (int) this.location.getY(), this.size);
        }
    }

    /**
     * notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }
}
