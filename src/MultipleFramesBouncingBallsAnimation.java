import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;

/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class MultipleFramesBouncingBallsAnimation {
    /**The 'x' and 'y' value of the left-up vertex of the first frame.
     */
    public static final int FIRST_LEFT_UP = 50;
    /**The 'x' and 'y' value of the right-down vertex of the first frame.
     */
    public static final int FIRST_RIGHT_DOWN = 500;
    /**The 'x' and 'y' value of the left-up vertex of the second frame.
     */
    public static final int SECOND_LEFT_UP = 450;
    /**The 'x' and 'y' value of the right-down vertex of the second frame.
     */
    public static final int SECOND_RIGHT_DOWN = 600;

    /**The function runs a bouncing ball animation,
     * while half of the balls bounce in one frame and the other half in another.
     * @param args an array contains sizes of balls.
     */
    public static void main(String[] args) {
        Ball[] array = new Ball[args.length];
        Frame frame1 = new Frame(FIRST_LEFT_UP, FIRST_LEFT_UP, FIRST_RIGHT_DOWN, FIRST_RIGHT_DOWN, Color.GRAY);
        Frame frame2 = new Frame(SECOND_LEFT_UP, SECOND_LEFT_UP, SECOND_RIGHT_DOWN, SECOND_RIGHT_DOWN, Color.YELLOW);
        CheckArgs myObj = new CheckArgs();
        boolean checks = myObj.isEmpty(args);
        /*
        makes sure the parameters-to-main are legal, if not - throws an exception
         */
        if (!checks) {
            return;
        }
        checks = myObj.isInteger(args);
        if (!checks) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            checks = myObj.isNegative(Integer.parseInt(args[i]));
            if (!checks) {
                return;
            }
        }
        //fills the first half of the array with random balls inside the borders of the first frame,
        //and the second half of the array with random balls inside the borders of the second frame.
        //if there is a non-even number of parameters - there will be one more ball at the second frame.
        for (int i = 0; i < args.length; i++) {
            int size = Integer.parseInt(args[i]);
            if (i < array.length / 2) {
                array[i] = new Ball(frame1, size);
            } else {
                array[i] = new Ball(frame2, size);
            }
        }
        GUI gui = new GUI("title", Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        //draws each one of the balls on the surface, shows the surface, waits for 50 milliseconds and so on
        //to create an animation
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            //paints rectangles on the surface
            frame1.drawFrame(d);
            frame2.drawFrame(d);
            for (int i = 0; i < array.length; i++) {
                array[i].moveOneStep();
                array[i].drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}