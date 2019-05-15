package operators;

import geometry.Point;
import geometry.Rectangle;
import instruments.Ball;
import instruments.Velocity;

/**Line is a segment composed by two points.
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return the "collision shape" of the object
      */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param collisionPoint the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter the 'ball hitter' parameter
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
