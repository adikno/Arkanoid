package display;

import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * In charge of running an animation.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;

    /**
     * A constructor.
     * @param gui a gui
     * @param framesPerSecond the number of frames should be take per second.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new biuoop.Sleeper();
    }

    /**
     * In charge of run the animation.
     * @param animation the animation that should be played.
     */
    public void run(Animation animation) {
        animation.setShouldStop();
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();

            double dt = 1;
            dt /= this.framesPerSecond;
            animation.doOneFrame(d, dt);
            this.gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;

            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
