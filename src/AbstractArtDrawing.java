import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class AbstractArtDrawing {

    /**This function draws 10 lines, drawn in black.
     * The middle point in each line is indicated in blue,
     * while the intersection points between the lines are indicated in red.
     */
    public void drawRandomLines() {
        // Create a window with the title "Random Lines"
        // which is 800 pixels wide and 600 pixels high.
        GUI gui = new GUI("Random Lines", Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT);
        DrawSurface d = gui.getDrawSurface();
        Line[] lines = new Line[10];
        for (int i = 0; i < 10; ++i) {
            //Enters a random-line to the array
            lines[i] = generateRandomLine();
            d.setColor(Color.BLACK);
            drawLine(lines[i], d);
            for (int j = 0; j < i; ++j) {
                //checks whether there is an intersection point between this line to the lines that already exist.
                Point intersection = lines[i].intersectionWith(lines[j]);
                //if there is an intersection point - paints it.
                if (intersection != null) {
                    d.setColor(Color.RED);
                    drawPoint(intersection, d);
                }
            }
            d.setColor(Color.BLUE);
            //paints the middle point of the line
            Point middle = lines[i].middle();
            drawPoint(middle, d);
            d.setColor(Color.BLACK);
        }
        gui.show(d);
    }

    /**The function creates a random line in the limits of the window.
     * @return a random line in the limits of the window
     */
    public Line generateRandomLine() {
        Random rand = new Random(); // create a random-number generator
        int x1 = rand.nextInt(Frame.DEFAULT_WIDTH) + 1; // get integer in range 1-800
        int y1 = rand.nextInt(Frame.DEFAULT_HEIGHT) + 1; // get integer in range 1-600
        int x2 = rand.nextInt(Frame.DEFAULT_WIDTH) + 1; // get integer in range 1-800
        int y2 = rand.nextInt(Frame.DEFAULT_HEIGHT) + 1; // get integer in range 1-600
        return new Line(x1, y1, x2, y2);
    }

    /**The function draws a line in the DrawSurface.
     * @param l a line
     * @param d a DrawSurface
     */
    public void drawLine(Line l, DrawSurface d) {
        int startX = (int) l.getStart().getX();
        int startY = (int) l.getStart().getY();
        int endX = (int) l.getEnd().getX();
        int endY = (int) l.getEnd().getY();
        d.drawLine(startX, startY, endX, endY);
    }

    /**The function draws a point in the DrawSurface (a filled circle with a radius of 3).
     * @param p a point
     * @param d a DrawSurface
     */
    public void drawPoint(Point p, DrawSurface d) {
        d.fillCircle((int) p.getX(), (int) p.getY(), 3);
    }
}
