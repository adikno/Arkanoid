package geometry;

import biuoop.DrawSurface;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public class Rectangle {
    private Point upperLeft;
    private Point upperRight;
    private Point lowerLeft;
    private Point lowerRight;
    private double width;
    private double height;

    /**
     * This function is a constructor of class Rectangle.
     * @param location the location of the upper-left vertex
     * @param w te width of the rectangle
     * @param h the height of the rectangle
     */
    public Rectangle(Point location, double w, double h) {
        this.height = h;
        this.width = w;
        this.upperLeft = location;
        this.upperRight = new Point(upperLeft.getX() + this.width, upperLeft.getY());
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + this.height);
        this.lowerRight = new Point(upperLeft.getX() + this.width, upperLeft.getY() + this.height);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * @param line a line
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List intersectionPoints(Line line) {
        List<Point> l = new LinkedList<>();
        //making lines depends on the rectangle vertex
        Line up = new Line(this.upperLeft, this.upperRight);
        Line lower = new Line(this.lowerLeft, this.lowerRight);
        Line left = new Line(this.upperLeft, this.lowerLeft);
        Line right = new Line(this.upperRight, this.lowerRight);
        //checks whether there is an intersection point with each one of the lines,
        //if there is - adds it to the list.
        Point p = up.intersectionWith(line);
        if (p != null) {
            l.add(p);
        }
        p = lower.intersectionWith(line);
        if (p != null) {
            l.add(p);
        }
        p = left.intersectionWith(line);
        if (p != null) {
            l.add(p);
        }
        p = right.intersectionWith(line);
        if (p != null) {
            l.add(p);
        }
        return l;
    }

    /**
     * Return the width of the rectangle.
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * returns the height of the rectangle.
     * @return the width of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-right vertex of the rectangle.
     * @return the upper-right vertex of the rectangle
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * Returns the lower-left vertex of the rectangle.
     * @return the lower-left vertex of the rectangle
     */
    public Point getLowerLeft() {
        return this.lowerLeft;
    }

    /**
     * Returns the lower-right vertex of the rectangle.
     * @return the lower-right vertex of the rectangle
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }

    /**
     * Returns the upper-left vertex of the rectangle.
     * @return the upper-left vertex of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * draws the rectangle on the surface.
     * @param surface a draw surface
     */
    public void drawRec(DrawSurface surface) {
        surface.drawRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * paints the rectangle on the surface.
     * @param surface a draw surface
     */
    public void fillRec(DrawSurface surface) {
        surface.fillRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }
}
