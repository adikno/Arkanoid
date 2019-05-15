package display;

/**
 * in charge of the menu.
 * @param <T> the type of the return value's selections.
 */
public interface Menu<T> extends Animation {
    /**
     * In charge of enter new selection option to the menu.
     * @param key a key to press
     * @param message a message
     * @param returnVal the outcome of the selection
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * return the current selection of the user.
     * @return the current selection of the user.
     */
    T getStatus();

    /**
     * In charge of enter new sub-menu to the menu.
     * @param key a key to press
     * @param message a message
     * @param subMenu the submenu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
