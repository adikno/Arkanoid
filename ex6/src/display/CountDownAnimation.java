package display;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountDownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int currentCount;
    private SpriteCollection gameScreen;
    private boolean stop;
    private biuoop.Sleeper sleeper;
    private long lastTime;

    /**
     * A constructor.
     * @param numOfSeconds the number of seconds the animation should take.
     * @param countFrom the number which the count-down should start from.
     * @param gameScreen the background that should be behind the count-down animation.
     */
    public CountDownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.currentCount = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.sleeper = new biuoop.Sleeper();
        this.lastTime = System.currentTimeMillis();
    }

    /**
     * Drawing the background and the current count on it as well as decrees 1 from the count.
     * If the count reach to 0 - finish.
     * @param d draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        long millisecondsPerFrame = (long) this.numOfSeconds * 1000 / 3;
        long startTime = this.lastTime;

        this.gameScreen.drawAllOn(d);
        d.setColor(Color.black);
        d.drawText(350, d.getHeight() / 2, String.valueOf(this.currentCount), 150);
        d.setColor(Color.white);
        d.drawText(355, d.getHeight() / 2 + 5, String.valueOf(this.currentCount), 150);

        long usedTime = System.currentTimeMillis() - startTime;
        if (this.currentCount < this.countFrom) {
            while (millisecondsPerFrame - usedTime > 0) {
                usedTime = System.currentTimeMillis() - startTime;
            }
        }
        this.currentCount -= 1;
        if (this.currentCount < 0) {
            this.stop = true;
        }
        this.lastTime = System.currentTimeMillis();
    }

    /**
     * Return false if the game should keep running and true otherwise.
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Prepare boolean members for reuse.
     */
    public void setShouldStop() {

    }
}