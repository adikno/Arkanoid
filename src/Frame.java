import biuoop.DrawSurface;

import java.awt.Color;

/**Frame represents the area that a ball can bounce in.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Frame {

    private Point p1;
    private Point p2;
    private Color color;

    /**The width of the screen.
     */
    public static final int DEFAULT_WIDTH = 800;
    /**The height of the screen.
     */
    public static final int DEFAULT_HEIGHT = 600;
    /**This function is a constructor of class Frame.
     * @param p1    is the left-up vertex of the rectangle
     * @param p2    is the right-down vertex of the rectangle
     * @param color is the color of the frame
     */
    public Frame(Point p1, Point p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    /**This function is a constructor of class Frame.
     * @param x1    is the 'x' of the left-up vertex of the rectangle
     * @param y1    is the 'y' of the left-up vertex of the rectangle
     * @param x2    is the 'x' of the right-down vertex of the rectangle
     * @param y2    is the 'y' of the right-down vertex of the rectangle
     * @param color is the color of the frame
     */
    public Frame(int x1, int y1, int x2, int y2, Color color) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
        this.color = color;
    }

    /**This function returns the left-up vertex of the rectangle.
     * @return the left-up vertex of the rectangle
     */
    public Point getP1() {
        return p1;
    }

    /**This function returns the right-down vertex of the rectangle.
     * @return the right-down vertex of the rectangle
     */
    public Point getP2() {
        return p2;
    }

    /**This function returns the color of the frame.
     * @return the color of the frame
     */
    public Color getColor() {
        return color;
    }

    /**This function draws the frame on the given DrawSurface.
     * @param surface a DrawSurface
     */
    public void drawFrame(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillRectangle((int) this.getP1().getX(), (int) this.getP1().getY(),
                (int) this.getP2().getX() - (int) this.getP1().getX(),
                (int) this.getP2().getY() - (int) this.getP1().getY());
    }
}
