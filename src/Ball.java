import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Random;

/**Ball is a moving circle with a center and a size.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class Ball {
    private int size;
    private Color color;
    private Point center;
    private Velocity velocity;
    private Frame frame;

    /**The maximal speed of a ball.
     */
    public static final int MAX_SPEED = 51;

    /**
     * This function is a constructor of class Ball.
     *
     * @param center is the center of ball
     * @param r      is the radios of the ball
     * @param color  is the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.size = r;
        this.color = color;
    }

    /**
     * This function is a constructor of class Ball.
     *
     * @param x     is the 'x' value of the center
     * @param y     is the 'y' value of the center
     * @param r     is the radios of the ball
     * @param color is the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.size = r;
        this.color = color;
    }

    /**
     * This function is a constructor of class Ball.
     *
     * @param frame the area that the ball can bounce in
     * @param size  is the radios of the ball
     */
    public Ball(Frame frame, int size) {
        this.frame = frame;
        this.size = size;
        Random rand = new Random();
        //Chooses random center so that all of the ball will be inside the borders.
        double x = rand.nextInt((int) frame.getP2().getX() - (int) frame.getP1().getX() - 2 * size);
        x += size + frame.getP1().getX();
        double y = rand.nextInt((int) frame.getP2().getY() - (int) frame.getP1().getY() - 2 * size);
        y += size + frame.getP1().getY();
        this.center = new Point(x, y);
        this.color = Color.BLACK;
        //Chooses random angel of movement and determines a speed in accordance the the size of the ball.
        if (size >= 50) {
            this.velocity = Velocity.fromAngleAndSpeed(rand.nextInt(359) + 1, 1);
        } else {
            this.velocity = Velocity.fromAngleAndSpeed(rand.nextInt(359) + 1, (51 - size) / 3);
        }
    }

    /**
     * This function returns the 'x' value of the center of the ball.
     *
     * @return the 'x' value of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * This function returns the 'y' value of the center of the ball.
     *
     * @return the 'y' value of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * This function returns the radios of the ball.
     *
     * @return the radios of the ball.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * This function returns the color of the ball.
     *
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * This function returns the velocity of the ball.
     *
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * This function draws the ball on the given DrawSurface.
     *
     * @param surface a DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }

    /**
     * This function set the velocity of the ball.
     *
     * @param v the velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * This function set the velocity of the ball.
     *
     * @param dx the the change in position of the `x' axe
     * @param dy the the change in position of the `y' axe
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * This function set the frame of the ball.
     *
     * @param f the frame of the ball
     */
    public void setFrame(Frame f) {
        this.frame = f;
    }

    /**
     * The function move the center of the ball one step i accordance to it's velocity,
     * and update it's direction of movement accordingly.
     * In case the use didn't invoke setVelocity before the function - throws exception.
     */
    public void moveOneStep() {
        try {
            /*
            If the ball is touching a border or about to touch a border at his next move - changes his direction.
             */
            if (this.center.getX() - this.size <= this.frame.getP1().getX() + ((MAX_SPEED - this.size) / 3) / 2
                    || this.center.getX() + this.size >= this.frame.getP2().getX() - ((MAX_SPEED - this.size) / 3) / 2) {
                this.setVelocity(-this.velocity.getDx(), this.velocity.getDy());
            }
            if (this.center.getY() + this.size >= this.frame.getP2().getY() - ((MAX_SPEED - this.size) / 3) / 2
                    || this.center.getY() - this.size <= this.frame.getP1().getY() + ((MAX_SPEED - this.size) / 3) / 2) {
                this.setVelocity(this.velocity.getDx(), -this.velocity.getDy());
            }
            this.center = this.getVelocity().applyToPoint(this.center);
        } catch (Exception e) {
            System.out.println("Error: The velocity isn't defined.");
        }
    }
}