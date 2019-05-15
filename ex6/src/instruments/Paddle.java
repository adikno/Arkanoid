package instruments;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import display.Sprite;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import operators.Collidable;

import java.awt.Color;
/**
 * @author Adi Knobel 209007087
 * @version "1.0, 17/04/18"
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle paddle;
    private biuoop.KeyboardSensor keyboard;
    private Color color;
    private int speed;
    private int initialSpeed;

    /**
     * This function is a constructor of class Paddle.
     * @param rec a rectangle object holds details about the size and the place of the paddle
     * @param keyBoard a keyboard sensor object who is in charge of reading the key presses
     * @param c the color of the paddle
     * @param speed the speed of the paddle
     */
    public Paddle(Rectangle rec, int speed, biuoop.KeyboardSensor keyBoard, Color c) {
        this.paddle = rec;
        this.speed = speed;
        this.initialSpeed = speed;
        this.color = c;
        this.keyboard = keyBoard;
    }

    /**
     * in charge of moving the paddle to the left while it isn't come out of the borders.
     */
    public void moveLeft() {
        Point newUpperLeft;
        if (this.paddle.getUpperLeft().getX() - this.speed >= GameLevel.WIDTH_FRAME) {
            newUpperLeft = new Point(this.paddle.getUpperLeft().getX() - this.speed, this.paddle.getUpperLeft().getY());
        } else {
            newUpperLeft = new Point(GameLevel.WIDTH_FRAME, this.paddle.getUpperLeft().getY());
        }
        this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(), this.paddle.getHeight());
    }
    /**
     * in charge of moving the paddle to the right while it isn't come out of the borders.
     */
    public void moveRight() {
        Point newUpperLeft;
        if (this.paddle.getUpperLeft().getX() + this.paddle.getWidth()
                + this.speed <= 800 - GameLevel.WIDTH_FRAME) {
            newUpperLeft = new Point(this.paddle.getUpperLeft().getX()
                    + this.speed, this.paddle.getUpperLeft().getY());
        } else {
            newUpperLeft = new Point(800 - GameLevel.WIDTH_FRAME - this.paddle.getWidth(),
                    this.paddle.getUpperLeft().getY());
        }
        this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(), this.paddle.getHeight());
    }

    /**
     * checking if the arrows keys are pressed and if so - moving the paddle.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
        this.speed = (int) (this.initialSpeed * dt);
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * drawing the paddle on the given surface.
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        this.paddle.fillRec(d);
        d.setColor(color.black);
        this.paddle.drawRec(d);
    }

    /**
     * returns the rectangle that construct the paddle.
     * @return this rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * the function changes the velocity of ball in accordance to which equally-spaced region it collided in.
     * @param collisionPoint the collision point between the ball and the paddle
     * @param currentVelocity the ball's velocity at the exact moment it collided in the paddle
     * @param hitter the Ball that's doing the hitting.
     * @return an updated velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //calculate the current speed of the ball
        double velocity = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());
        //divide the paddle to 5 equally-spaced regions
        double devision = this.paddle.getWidth() / 5;
        if (collisionPoint.getX() <= this.paddle.getUpperLeft().getX() + devision) {
            return Velocity.fromAngleAndSpeed(300, velocity);
        }
        if (collisionPoint.getX() > this.paddle.getUpperLeft().getX() + devision
                && collisionPoint.getX() <= this.paddle.getUpperLeft().getX() + (devision * 2)) {
            return Velocity.fromAngleAndSpeed(330, velocity);
        }
        if (collisionPoint.getX() > this.paddle.getUpperLeft().getX() + (devision * 3)
                && collisionPoint.getX() <= this.paddle.getUpperLeft().getX() + (devision * 4)) {
            return Velocity.fromAngleAndSpeed(30, velocity);
        }
        if (collisionPoint.getX() > this.paddle.getUpperLeft().getX() + (devision * 4)) {
            return Velocity.fromAngleAndSpeed(60, velocity);
        }
        return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
    }

    /**
     * Add this paddle to the game.
     * @param g a game object
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Moving the paddle to the canter of the screen.
     */
    public void moveToCenter() {
        Point newUpperLeft = new Point((800 - this.paddle.getWidth()) / 2,
                600 - GameLevel.HEIGHT_PADDLE);
        this.paddle = new Rectangle(newUpperLeft, this.paddle.getWidth(), GameLevel.HEIGHT_PADDLE);
    }
}