package geometry;

import biuoop.DrawSurface;
import display.Sprite;
import instruments.Ball;

import java.awt.Color;
import java.util.List;

/**Line is a segment composed by two points.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Line implements Sprite {
    public static final double DEVIATION = 0.005;
    private Point start;
    private Point end;
    private Color color;

    /**This function is a constructor of class Line.
     * @param first first point of the segment
     * @param last  last point of the segment
     */
    public Line(Point first, Point last) {
        this.start = first;
        this.end = last;
    }

    /**
     * This function is a constructor of class Line.
     * @param first first point of the segment
     * @param last  last point of the segment
     * @param color the color of the line
     */
    public Line(Point first, Point last, Color color) {
        this.start = first;
        this.end = last;
        this.color = color;
    }

    /**This function is a constructor of class Line.
     * @param x1 value of x in the first point of the segment
     * @param y1 value of y in the first point of the segment
     * @param x2 value of x in the second point of the segment
     * @param y2 value of y in the second point of the segment
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * This function is a constructor of class Line.
     * @param ball with a center and a velocity that will define the new line starting at current location,
     * and ending where the velocity will take the ball if no collisions will occur
     */
    public Line(Ball ball) {
        this.start = new Point(ball.getX(), ball.getY());
        this.end = new Point(ball.getX() + ball.getTempV().getDx(), ball.getY() + ball.getTempV().getDy());
    }

    /**This function calculate the length of the line.
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**This function calculate the middle point of the line.
     * @return the middle point of the line
     */
    public Point middle() {
        double newX = (this.start.getX() + this.end.getX()) / 2;
        double newY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(newX, newY);
    }

    /**This function returns the start point of the line.
     * @return the start point of the line
     */
    public Point getStart() {
        return this.start;
    }

    /**This function returns the end point of the line.
     * @return the end point of the line
     */
    public Point getEnd() {
        return this.end;
    }

    /**This function checks if the lines intersect.
     * @param other another line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        Point p = this.intersectionWith(other);
        return (p != null);
    }

    /**Returns the intersection point if the lines intersect, and null otherwise.
     * (while considering a possible deviation of DEVIATION in the calculation).
     * @param other another line
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // Current line represented as a1x + b1y = c1
        double a1 = this.end.getY() - this.start.getY();
        double b1 = this.start.getX() - this.end.getX();
        double c1 = (a1 * this.start.getX()) + (b1 * this.start.getY());
        // Other line represented as a2x + b2y = c2
        double a2 = other.end.getY() - other.start.getY();
        double b2 = other.start.getX() - other.end.getX();
        double c2 = (a2 * other.start.getX()) + (b2 * other.start.getY());
        double determinant = a1 * b2 - a2 * b1;
        //If the lines are parallel - there is no a intersection unless one continues the other.
        //If one of the lines is actually a point - return null no mather what.
        if (determinant == 0) {
            if (this.start == other.start || this.start == other.end) {
                return this.start;
            }
            if (this.end == other.end || this.end == other.start) {
                return this.end;
            }
            return null;
        }
        //Calculate the intersection point between the two equations of a line.
        double newX = (b2 * c1 - b1 * c2) / determinant;
        double newY = (a1 * c2 - a2 * c1) / determinant;
        /*
        Checks whether the point is on the segment, if not - return null.
        (while considering a possible deviation of DEVIATION in the calculation).
         */
        if (Math.max(this.start.getX(), this.end.getX()) < newX - DEVIATION
                || Math.min(this.start.getX(), this.end.getX()) > newX + DEVIATION) {
            return null;
        }
        if (Math.max(other.start.getX(), other.end.getX()) < newX - DEVIATION
                || Math.min(other.start.getX(), other.end.getX()) > newX + DEVIATION) {
            return null;
        }
        if (Math.max(this.start.getY(), this.end.getY()) < newY - DEVIATION
                || Math.min(this.start.getY(), this.end.getY()) > newY + DEVIATION) {
            return null;
        }
        if (Math.max(other.start.getY(), other.end.getY()) < newY - DEVIATION
                || Math.min(other.start.getY(), other.end.getY()) > newY + DEVIATION) {
            return null;
        }
        return new Point(newX, newY);
    }

    /**This function checks if the lines are equal.
     * @param other another line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if ((this.start == other.start) && (this.end == other.end)) {
            return true;
        }
        return ((this.start == other.end) && (this.end == other.start));
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     * @param rect a rectangle
     * @return the closest intersection point to the start of the line if there is one
     *         otherwise return null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //a list of all the intersection points between the line and the rectangle
        List<Point> l = (List<Point>) rect.intersectionPoints(this);
        int i = 0;
        Point minPoint = null;
        if (!l.isEmpty()) {
            double minDistance = this.start.distance(l.get(0));
            minPoint = l.get(0);
            //searching for the closest intersection point to the start of the line
            for (Point p: l) {
                if (this.start.distance(l.get(i)) < minDistance) {
                    minDistance = this.start.distance(l.get(i));
                    minPoint = l.get(i);
                }
                i++;
            }
        }
        return minPoint;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(), (int) this.end.getY());
    }

    /**
     * notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }
}
