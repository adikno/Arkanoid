package instruments;

import display.Task;

/**
 * in charge of union info about each selection option in the menu.
 */
public class Selection {
    private String key;
    private String message;
    private Task<Void> returnVal;

    /**
     * a constructor.
     * @param key a key to press
     * @param message a message
     * @param returnVal the outcome of the selection
     */
    public Selection(String key, String message, Task<Void> returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * a getter.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * a getter.
     * @return the massage
     */
    public String getMessage() {
        return message;
    }

    /**
     * a getter.
     * @return the outcome of the selection
     */
    public Task<Void> getReturnVal() {
        return this.returnVal;
    }
}
