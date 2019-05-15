package display;

/**
 * An interface represents something that we can run.
 * @param <T> the return type from the 'run' method.
 */
public interface Task<T> {

    /**
     * run the task.
     * @return a return value
     */
    T run();
}