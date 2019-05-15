/**Line is a segment composed by two points.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Line {
    private Point start;
    private Point end;

    /**This function is a constructor of class Line.
     * @param start first point of the segment
     * @param end   last point of the segment
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
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
         */
        if (Math.max(this.start.getX(), this.end.getX()) < newX
                || Math.min(this.start.getX(), this.end.getX()) > newX) {
            return null;
        }
        if (Math.max(other.start.getX(), other.end.getX()) < newX
                || Math.min(other.start.getX(), other.end.getX()) > newX) {
            return null;
        }
        if (Math.max(this.start.getY(), this.end.getY()) < newY
                || Math.min(this.start.getY(), this.end.getY()) > newY) {
            return null;
        }
        if (Math.max(other.start.getY(), other.end.getY()) < newY
                || Math.min(other.start.getY(), other.end.getY()) > newY) {
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
}
