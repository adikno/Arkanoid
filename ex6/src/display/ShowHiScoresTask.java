package display;

/**
 * In charge of run the high-scores animation.
 */
public class ShowHiScoresTask implements Task<Void> {
    private final AnimationRunner runner;
    private final Animation highScoresAnimation;

    /**
     * a constructor.
     * @param runner an animation runner
     * @param highScoresAnimation an animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }
    /**
     * In charge of run the high-scores animation.
     * @return null as return value
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
