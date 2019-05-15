package operators;

/**
 * Counter is in charge of counting objects.
 */
public class Counter {
    private int num;

    /**
     * Default constructor.
     */
    public Counter() {
        this.num = 0;
    }

    /**
     * A constructor.
     * @param num the start-value of the counter.
     */
    public Counter(int num) {
        this.num = num;
    }

    /**
     * add number to current count.
     * @param number add number to current count.
     */
    public void increase(int number) {
        this.num += number;
    }

    /**
     * subtract number to current count.
     * @param number subtract number to current count.
     */
    public void decrease(int number) {
        this.num -= number;
    }

    /**
     * reset the counter.
     */
    public void reset() {
        this.num = 0;
    }
    /**
     * get current count.
     * @return current count.
     */
    public int getValue() {
        return this.num;
    }
}