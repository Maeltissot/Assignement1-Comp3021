package game;

import util.StringUtils;

/**
 * Class representing the delay before the water starts to flow in the map.
 */
class DelayBar {

    private final int initialValue;
    private int currentValue;

    /**
     * Constructs a {@link DelayBar}.
     *
     * @param initialValue Number of rounds to wait before the water starts to flow.
     */
    DelayBar(int initialValue) {
        this.initialValue = initialValue;
        this.currentValue = initialValue;
    }

    /**
     * Decrements the current value by 1.
     */
    void countdown() {
        // TODO - DONE
        currentValue--;
    }

    /**
     * <p>
     * Hint: The value of {@code currentValue} will roll into negative once the delay ends. You may use the absolute
     * value of this number to compute the number of valid moves since the delay has ended.
     * </p>
     *
     * @return The distance the water should flow at this stage.
     */
    int distance() {
        // TODO - DONE
        if(initialValue-currentValue<5){
            return 0;
        } else {
            return Math.abs(currentValue);
        }
        }


    void display() {
        if (currentValue > 0) {
            System.out.print("Rounds Countdown: ");
            System.out.print(StringUtils.createPadding(currentValue, '='));
            System.out.print(StringUtils.createPadding(initialValue - currentValue, ' '));
            System.out.print(" " + currentValue);
            System.out.println();
        } else {
            System.out.println();
        }
    }
}
