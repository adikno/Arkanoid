package display;

import java.io.Serializable;

/**
 * Union high-score info.
 */
public class ScoreInfo implements Comparable, Serializable {
    private String name;
    private int score;

    /**
     * a constructor.
     * @param name the userName
     * @param score tha score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * a getter.
     * @return the userName
     */
    public String getName() {
        return this.name;
    }

    /**
     * a getter.
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Object o) {
        if (this.score > ((ScoreInfo) o).getScore()) {
            return 1;
        } else if (this.getScore() == ((ScoreInfo) o).getScore()) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.name + "        " + String.valueOf(this.score);
    }
}