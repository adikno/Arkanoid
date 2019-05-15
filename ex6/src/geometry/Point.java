package geometry;
/**
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public class Point {
    private double x;
    private double y;
    /**This function is a constructor of class Point.
     * @param a value of x
     * @param b value of y
     */
    public Point(double a, double b) {
        this.x = a;
        this.y = b;
    }
    /**This function calculates the distance of this point to the other point.
     * @param other another point
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        double newX = this.x - other.getX();
        double newY = this.y - other.getY();
        return Math.sqrt((newX * newX) + (newY * newY));
    }

    /**This function checks if the points are equal.
     * @param other another point
     * @return true if the points are equal, false otherwise
     * (while considering a possible deviation of 0.005 in the calculation)
     */
    public boolean equals(Point other) {
        return (Math.abs(this.x - other.getX()) <= Line.DEVIATION && Math.abs(this.y - other.getY()) <= Line.DEVIATION);
    }
    /** This function returns the x value of the point.
     * @return the x value of the point
     */
    public double getX() {
        return this.x;
    }
    /** This function returns the y value of the point.
     * @return the y value of the point
     */
    public double getY() {
        return this.y;
    }
}
