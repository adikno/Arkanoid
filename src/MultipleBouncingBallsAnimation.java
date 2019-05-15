import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;

/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class MultipleBouncingBallsAnimation {
    /**The function runs a bouncing balls animation.
     * @param args an array contains sizes of balls.
     */
    public static void main(String[] args) {
        Ball[] array = new Ball[args.length];
        Frame frame = new Frame(0, 0, Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT, Color.BLACK);
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
        //fills the array with random balls inside the borders
        for (int i = 0; i < args.length; i++) {
            int size = Integer.parseInt(args[i]);
            array[i] = new Ball(frame, size);
        }
        GUI gui = new GUI("title", Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        //draws each one of the balls on the surface, shows the surface, waits for 50 milliseconds and so on
        //to create an animation
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (int i = 0; i < array.length; i++) {
                array[i].moveOneStep();
                array[i].drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
