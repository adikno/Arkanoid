package game;

import display.Sprite;
import geometry.Point;
import geometry.Rectangle;
import instruments.Block;
import instruments.Velocity;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

/**
 * a generic levelInformation class.
 */
public class Level implements LevelInformation {
    private String name;
    private int[] velocities;
    private int[] angles;
    private Image image;
    private Color backGround;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Block> blocks;
    private int numBlocks;

    /**
     * A constructor.
     * @param name the level name
     * @param velocities the ball's velocities
     * @param angles the ball's angles
     * @param paddleSpeed the paddle speed
     * @param paddleWidth the paddle width
     * @param blocks a list of blocks
     * @param numBlocks the number of blocks
     */
    public Level(String name, int[] velocities, int[] angles, int paddleSpeed, int paddleWidth,
                 List<Block> blocks, int numBlocks) {
        this.name = name;
        this.velocities = velocities;
        this.angles = angles;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocks = blocks;
        this.numBlocks = numBlocks;
    }

    /**
     * set the background color.
     * @param color the background color.
     */
    public void setBackGroundColor(Color color) {
        this.backGround = color;
        this.image = null;
    }

    /**
     * set the background image.
     * @param img the background image.
     */
    public void setBackGroundImg(Image img) {
        this.image = img;
        this.backGround = null;
    }

    /**
     * Return the number of balls in the level.
     *
     * @return the number of balls in the level
     */
    public int numberOfBalls() {
        return this.velocities.length;
    }

    /**
     * Return a list of initial velocities of each ball.
     *
     * @return a list of initial velocities of each ball
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new LinkedList<>();
        for (int i = 0; i < this.numberOfBalls(); i++) {
            v.add(Velocity.fromAngleAndSpeed(this.angles[i], this.velocities[i]));
        }
        return v;
    }

    /**
     * Return the speed of the paddle.
     *
     * @return the speed of the paddle
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Return the width of the paddle.
     *
     * @return the width of the paddle.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Return the level-name.
     *
     * @return the level-name.
     */
    public String levelName() {
        return this.name;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        Rectangle rec = new Rectangle(new Point(0, 0), 800, 600);
        if (this.image != null) {
            return new Block(rec, this.image);
        } else {
            return new Block(rec, this.backGround);
        }
    }

    /**
     * A list of Blocks that make up this level.
     *
     * @return a list of Blocks that make up this level
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Return the number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return the number of blocks that should be removed before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }
}
