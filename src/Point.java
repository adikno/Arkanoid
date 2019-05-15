/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Point {
    private double x;
    private double y;
    /**This function is a constructor of class Point.
     * @param x value of x
     * @param y value of y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
     */
    public boolean equals(Point other) {
        if (this.x == other.getX() && this.y == other.getY()) {
            return true;
        }
        return (this.x == other.getY() && this.y == other.getX());
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
