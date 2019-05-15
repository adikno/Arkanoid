package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import display.Sprite;
import display.SpriteCollection;
import display.AnimationRunner;
import display.ScoreIndicator;
import display.LevelNameIndicator;
import display.CountDownAnimation;
import display.LivesIndicator;
import display.KeyPressStoppableAnimation;
import display.Animation;
import geometry.Point;
import geometry.Rectangle;
import instruments.Ball;
import display.PauseScreen;
import instruments.Block;
import instruments.Paddle;
import operators.HitListener;
import operators.Counter;
import operators.Collidable;
import operators.BallRemover;
import operators.BlockRemover;
import operators.ScoreTrackingListener;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public class GameLevel implements HitListener, Animation {
    /**
     * a default height for a block.
     */
    public static final int HEIGHT_BLOCK = 25;
    /**
     * a default width for a frame-block.
     */
    public static final int WIDTH_FRAME = 25;
    /**
     * height of a paddle.
     */
    public static final int HEIGHT_PADDLE = 20;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter ballsCounter;
    private Counter score;
    private Counter numOfLives;
    private Paddle paddle;
    private AnimationRunner runner;
    private biuoop.KeyboardSensor keyboard;
    private boolean running;
    private LevelInformation information;
    private List<Block> blocks;

    /**
     * This function is a constructor of class Game.
     * @param levelInformation the level information
     * @param ar the animation runner
     * @param key a keyboard sensor
     * @param livesCounter a lives counter
     * @param score the score counter
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner ar, KeyboardSensor key,
                     Counter livesCounter, Counter score) {
        this.information = levelInformation;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(this.information.numberOfBlocksToRemove());
        this.ballsCounter = new Counter();
        this.numOfLives = livesCounter;
        this.score = score;
        this.keyboard = key;
        this.runner = ar;
        this.running = true;
        this.blocks = new LinkedList<>();
    }

    /**
     * adding a collidable object to the collection.
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * adding a sprite object to the collection.
     * @param s sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removing Collidable object from the game-environment.
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removing Sprite object from the Sprite list.
     * @param s the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Adding new balls to the game and move the paddle to the center.
     * @param num the required number of balls
     */
    public void createBallsOnTopOfPaddle(int num) {
        for (int i = 0; i < num; i++) {
            Ball ball = new Ball(this.environment, 5,
                    new Point(800 / 2, 600 - HEIGHT_PADDLE - 5));
            ball.setVelocity(this.information.initialBallVelocities().get(i));
            ball.addToGame(this);
        }
        this.ballsCounter.increase(num);
        this.paddle.moveToCenter();
    }

    /**
     * Initialize a new game: create the Blocks, paddle, two Balls and add them to the game.
     */
    public void initialize() {
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.ballsCounter);
        ScoreTrackingListener tracking = new ScoreTrackingListener(this.score);

        Block indicator = new Block(new Rectangle(new Point(0, 0),
                800, HEIGHT_BLOCK), Color.white);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        LivesIndicator livesIndicator = new LivesIndicator(this.numOfLives);
        LevelNameIndicator nameIndicator = new LevelNameIndicator(this.information.levelName());

        //adding the background to the sprites-list
        this.sprites.addSprite(this.information.getBackground());

        //creating the frame of the screen
        Block b1 = new Block(new Rectangle(new Point(0, HEIGHT_BLOCK), 800, WIDTH_FRAME),
                Color.gray);
        Block b2 = new Block(new Rectangle(new Point(0, 0), WIDTH_FRAME, 600), Color.gray);
        Block b3 = new Block(new Rectangle(new Point(800 - WIDTH_FRAME, 0), WIDTH_FRAME,
                600),
                Color.gray);
        Block deathBlock = new Block(new Rectangle(new Point(0, 600 + 10),
                800, WIDTH_FRAME),
                Color.black);
        b1.addToGame(this);
        b2.addToGame(this);
        b3.addToGame(this);
        deathBlock.addToGame(this);
        deathBlock.addHitListener(ballRemover);

        indicator.addToGame(this);
        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        nameIndicator.addToGame(this);

        this.blocks.addAll(this.information.blocks());
        //adding the blocks from the level-information to the game
        for (Block b: this.blocks) {
            b.addHitListener(blockRemover);
            b.addHitListener(tracking);
            b.addToGame(this);
        }

        Point location = new Point((800 - this.information.paddleWidth()) / 2,
                600 - HEIGHT_PADDLE);
        this.paddle = new Paddle(new Rectangle(location, this.information.paddleWidth(), HEIGHT_PADDLE),
                this.information.paddleSpeed(), this.keyboard, Color.orange);
        this.paddle.addToGame(this);
    }

    /**
     * Run one turn of the level.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle(this.information.numberOfBalls());
        // countdown before turn starts.
        this.runner.run(new CountDownAnimation(2, 3, this.sprites));
        // use our runner to run the current display -- which is one turn of the game.
        this.running = true;
        this.runner.run(this);
    }

    /**
     * Removing all the blocks from the game when it over.
     */
    public void removeAllBlocks() {
        for (Block block: this.blocks) {
            block.removeFromGame(this);
        }
    }

    /**
     * Return the remaining number of blocks.
     * @return the remaining number of blocks.
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the block that is being hitted
     * @param hitter the Ball that's doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {

    }

    /**
     * Draw all the sprites and notified all that time passed.
     * Check whether the animation runner should stop.
     * @param d the draw-surface
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.ballsCounter.getValue() == 0) {
            this.numOfLives.decrease(1);
            this.running = false;
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
    }

    /**
     * Return false if the game should keep running and true otherwise.
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * preparing the boolean members for reuse.
     */
    public void setShouldStop() {
        this.running = true;
    }
}