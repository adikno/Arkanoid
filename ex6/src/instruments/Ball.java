package instruments;

import biuoop.DrawSurface;
import display.Sprite;
import game.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import operators.CollisionInfo;
import java.awt.Color;

/**Ball is a moving circle with a center and a size.
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public class Ball implements Sprite {
    /**A default speed for a ball.
     */
    public static final int DEFAULT_SPEED = 6;
    /**Degrees in circle minus 1.
     */
    public static final int DEGREES = 359;

    private int size;
    private Color color;
    private Point center;
    private Velocity velocity;
    private GameEnvironment gameEnv;
    private Line trajectory;
    private Velocity tempV;
    /**
     * This function is a constructor of class Ball.
     *
     * @param place is the center of ball
     * @param r      is the radios of the ball
     * @param c  is the color of the ball
     */
    public Ball(Point place, int r, Color c) {
        this.center = place;
        this.size = r;
        this.color = c;
        this.gameEnv = new GameEnvironment();
    }

    /**
     * This function is a constructor of class Ball.
     *
     * @param x     is the 'x' value of the center
     * @param y     is the 'y' value of the center
     * @param r     is the radios of the ball
     * @param c is the color of the ball
     */
    public Ball(double x, double y, int r, Color c) {
        this.center = new Point(x, y);
        this.size = r;
        this.color = c;
    }

    /**
     * This function is a constructor of class Ball.
     *
     * @param environment the area that the ball can bounce in
     * @param r  is the radios of the ball
     * @param place the center of the ball
     */
    public Ball(GameEnvironment environment, int r, Point place) {
        this.gameEnv = environment;
        this.size = r;
        this.center = place;
        this.color = Color.white;
    }

    /**
     * This function returns the 'x' value of the center of the ball.
     *
     * @return the 'x' value of the center of the ball.
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * This function returns the 'y' value of the center of the ball.
     *
     * @return the 'y' value of the center of the ball.
     */
    public double getY() {
        return this.center.getY();
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
    public Color getColor() {
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
     * This function returns the velocity of the ball.
     *
     * @return the velocity of the ball.
     */
    public Velocity getTempV() {
        return this.tempV;
    }

    /**
     * This function draws the ball on the given DrawSurface.
     *
     * @param surface a DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.getX(), (int) this.getY(), this.size);
        surface.setColor(Color.black);
        surface.drawCircle((int) this.getX(), (int) this.getY(), this.size);
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
     * @param environment the game environment of the ball
     */
    public void setGameEnv(GameEnvironment environment) {
        this.gameEnv = environment;
    }

    /**
     * The function move the ball to "almost" the hit point, but just slightly before it.
     * (while considering a possible deviation of Line.DEVIATION in the calculation of the collision point).
     *
     * @param currCollision contains detail about the current collision of the ball
     * @return Point represents the place the ball should move to.
     */
    public Point centerAfterHit(CollisionInfo currCollision) {
        double newX = currCollision.collisionPoint().getX();
        double newY = currCollision.collisionPoint().getY();
        if (Math.abs(currCollision.collisionPoint().getX()
                - currCollision.collisionObject().getCollisionRectangle().getUpperLeft().getX()) <= Line.DEVIATION) {
            newX -= this.size;
        }
        if (Math.abs(currCollision.collisionPoint().getX()
                - currCollision.collisionObject().getCollisionRectangle().getUpperRight().getX()) <= Line.DEVIATION) {
            newX += this.size;
        }
        if (Math.abs(currCollision.collisionPoint().getY()
                - currCollision.collisionObject().getCollisionRectangle().getUpperLeft().getY()) <= Line.DEVIATION) {
            newY -= this.size;
        }
        if (Math.abs(currCollision.collisionPoint().getY()
                - currCollision.collisionObject().getCollisionRectangle().getLowerLeft().getY()) <= Line.DEVIATION) {
            newY += this.size;
        }
        return new Point(newX, newY);
    }

    /**
     * The function check whether the ball will hit a collidable object at his next move.
     * if so - move the center of the ball to "almost" the hit point and update it's velocity,
     * if not - move it one step forward.
     * In case the use didn't invoke setVelocity before the function - throws exception.
     */
    public void moveOneStep() {
        try {
            //creating a line starting at current location,
            //and ending where the velocity will take the ball if no collisions will occur
            this.trajectory = new Line(this);
            //details about the imminent collision if there is one
            CollisionInfo currCollision = this.gameEnv.getClosestCollision(this.trajectory);
            /*
            if there is a collision expected - update the ball's center and velocity accordingly
             */
            if (currCollision != null) {
                //changing the velocity accordingly
                Velocity temp = currCollision.collisionObject().hit(this, currCollision.collisionPoint(),
                        this.velocity);
                //if the velocity has been changed - move the ball to "almost" the hit point,
                // otherwise - move the ball one step forward
                if (this.velocity.getDx() != temp.getDx() || this.velocity.getDy() != temp.getDy()) {
                    this.velocity = temp;
                    this.center = centerAfterHit(currCollision);
                } else {
                    this.center = this.tempV.applyToPoint(this.center);
                }
            } else {
                this.center = this.tempV.applyToPoint(this.center);
            }
        } catch (Exception e) {
            System.out.println("Error: The velocity isn't defined.");
        }
    }
    /**
     notify the sprite that time has passed.
     @param dt specifies the amount of seconds passed since the last call
      */
    public void timePassed(double dt) {
        this.tempV = new Velocity(this.velocity.getDx() * dt, this.velocity.getDy() * dt);
        this.moveOneStep();
    }

    /**
     * adding the ball to the game.
     * @param game a game object
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Removing the ball from the game.
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}