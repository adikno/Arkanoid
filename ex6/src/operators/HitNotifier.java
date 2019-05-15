package operators;

/**
 * An interface in charge of notify hit-listeners that an object is being-hit.
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     * @param hl an hit-listener.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl an hit-listener.
     */
    void removeHitListener(HitListener hl);
}