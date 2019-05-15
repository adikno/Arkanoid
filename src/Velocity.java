/**Velocity specifies the change in position of the `x' and the `y` axes.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Velocity {
    private double dx;
    private double dy;

    /**This function is a constructor of class Velocity.
     * @param dx the change in position of the `x' axe
     * @param dy the velocity of x
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**This function returns the change in position of the `x' axe.
     * @return the change in position of the `x' axe
     */
    public double getDx() {
        return this.dx;
    }

    /**This function returns the change in position of the `y' axe.
     * @return the change in position of the `y' axe
     */
    public double getDy() {
        return this.dy;
    }

    /**This function converts the velocity from angle and speed to dx and dy.
     * @param angle the direction of the movement
     * @param speed the number of units the ball should move
     * @return a new Velocity obj
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = speed * Math.cos(Math.toRadians(angle)) * -1;
        return new Velocity(dx, dy);
    }

    /**The function takes a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p a point with position (x,y)
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + this.dx;
        double y = p.getY() + this.dy;
        return new Point(x, y);
    }
}