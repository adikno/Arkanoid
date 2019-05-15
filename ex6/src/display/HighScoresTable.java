package display;

import java.io.FileInputStream;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.LinkedList;

/**
 * in charge of maintaining tha high-scores file.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> list;
    private int size;
    /**
     * default size for a table.
     */
    public static final int DEFAULT_SIZE = 5;

    /**
     * Create an empty high-scores table with the specified size.
     * @param size the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.list = new LinkedList<>();
    }

    /**
     * in charge of adding a high score to tha table.
     * @param score score-info object
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        //adding the score just in case it really is a high-score
        if (rank <= this.size) {
            this.list.add(rank - 1, score);
        }
        //make sure the number of high-score isn't exceed from the size of the table
        List<ScoreInfo> temp = new LinkedList<>();
        int i = 1;
        for (ScoreInfo s: this.list) {
            temp.add(s);
            if (i == size) {
                break;
            }
            i++;
        }
        this.list = temp;
    }

    /**
     * a getter.
     * @return the size of the table.
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.list;
    }

    /**
     * return the rank of the current score.
     * @param score a new score
     * @return the rank of the current score.
     */
    public int getRank(int score) {
        int i = 1;
        if (!this.list.isEmpty()) {
            for (ScoreInfo s: this.list) {
                if (s.getScore() >= score) {
                    i++;
                } else {
                    return i;
                }
            }
        }
        return i;
    }

    /** Clears the table.
     */
    public void clear() {
        this.list.removeAll(this.list);
    }

    /**
     * Load table data from file.
     * @param filename a file
     * @throws IOException an optional exception
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            ScoreInfo temp;
            while ((temp = (ScoreInfo) objectInputStream.readObject()) != null) {
                this.add(temp);
            }
        } catch (FileNotFoundException e) { // Can't find file to open
            return;
        } catch (IOException e) { // Some other problem
            return;
        } catch (ClassNotFoundException e) {
            return;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename a file
     * @throws IOException an optional exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename));
            for (ScoreInfo s: this.list) {
                objectOutputStream.writeObject(s);
            }
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     * @param filename a file
     * @return a table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable hst = new HighScoresTable(DEFAULT_SIZE);
        try {
            hst.load(filename);
            return hst;
        } catch (IOException e) {
            return hst;
        }
    }
}
