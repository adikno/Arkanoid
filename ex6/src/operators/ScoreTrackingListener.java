package operators;

import instruments.Ball;
import instruments.Block;

/**
 * A Hit-listener which in charge of tracking the score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * A constructor.
     * @param scoreCounter the current score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Update the score counter in accordance to the current hit-event.
     * @param beingHit the being-hit block
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getNumOfHits() != -1) {
            this.currentScore.increase(5);
            if (beingHit.getNumOfHits() == 0) {
                this.currentScore.increase(10);
            }
        }
    }
}