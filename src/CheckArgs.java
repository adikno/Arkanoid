/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class CheckArgs {
    /**The function makes sure the array contain integers.
     * @param args an array of strings
     * @return true if the array contains integers and false otherwise
     */
    public boolean isInteger(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                int size = Integer.parseInt(args[i]);
            }
        } catch (Exception exception) {
            System.out.println("Error: Not all the numbers are integers.");
            return false;
        }
        return true;
    }

    /**The function makes sure the array is not empty.
     * @param args an array of strings
     * @return true if the array is not empty and false otherwise
     */
    public boolean isEmpty(String[] args) {
        try {
            String size = args[0];
        } catch (Exception exception) {
            System.out.println("Error: There are no argument to main.");
            return false;
        }
        return true;
    }

    /**The function makes sure the number is positive.
     * @param arg an array of strings
     * @return true if the the number is positive and false otherwise
     */
    public boolean isNegative(int arg) {
        try {
            if (arg <= 0) {
                throw new IllegalArgumentException("Error: Non-positive number entered.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
