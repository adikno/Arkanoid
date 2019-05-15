package game;

import display.Sprite;
import instruments.Block;
import instruments.Velocity;

import java.util.List;

/**
 * An interface containing all the information level should has.
 */
public interface LevelInformation {

    /**
     * Return the number of balls in the level.
     * @return the number of balls in the level
     */
    int numberOfBalls();

    /**
     * Return a list of initial velocities of each ball.
     * @return a list of initial velocities of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * Return the speed of the paddle.
     * @return the speed of the paddle
     */
    int paddleSpeed();

    /**
     * Return the width of the paddle.
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * Return the level-name.
     * @return the level-name.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * A list of Blocks that make up this level.
     * @return a list of Blocks that make up this level
     */
    List<Block> blocks();

    /**
     * Return the number of blocks that should be removed before the level is considered to be "cleared".
     * @return the number of blocks that should be removed before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}