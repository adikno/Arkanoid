package operators;

import java.awt.Color;

/**
 * in charge of making a color out of string.
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     * @param s color definition
     * @return the specified color
     */
    public java.awt.Color awtColorFromString(String s) {
        if (s.equals("black")) {
            return Color.black;
        } else if (s.equals("blue")) {
            return Color.blue;
        } else if (s.equals("cyan")) {
            return Color.cyan;
        } else if (s.equals("gray")) {
            return Color.gray;
        } else if (s.equals("lightGray")) {
            return Color.lightGray;
        } else if (s.equals("green")) {
            return Color.green;
        } else if (s.equals("orange")) {
            return Color.orange;
        } else if (s.equals("pink")) {
            return Color.pink;
        } else if (s.equals("red")) {
            return Color.red;
        } else if (s.equals("white")) {
            return Color.white;
        } else {
            return Color.yellow;
        }
    }

    /**
     * parse color definition and return the specified color.
     * @param s color definition
     * @return the specified color
     */
    public java.awt.Color rgbColorFromString(String s) {
        String[] split = s.substring(s.indexOf("(") + 1, s.indexOf(")")).split(",");
        return new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}