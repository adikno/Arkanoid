package operators;

import instruments.Ball;
import instruments.Block;

/**
 * Hit listener is in charge of acting whenever an object is hit.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the being-hit block
     * @param hitter the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}