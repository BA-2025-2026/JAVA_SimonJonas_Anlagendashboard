package net.ictcampus.semodul.anlagendashboard.utility;

public class FinanceMathUtil {

    // Prevent creation of instances (private keyword)
    private FinanceMathUtil() {}

    /**
     * Calculates the absolute difference between the start value and the end value.
     *
     * @param startValue The initial value (must be non-negative).
     * @param endValue   The final value (must be non-negative).
     * @return The absolute performance (endValue - startValue).
     * @throws IllegalArgumentException if either startValue or endValue is negative.
     */
    public static double calculateAbsolutePerformance(double startValue, double endValue) {
        if (startValue < 0 || endValue < 0) throw new IllegalArgumentException("Values for performance calculation cannot be negative");
        return endValue - startValue;
    }

    /**
     * Calculates the relative performance (percentage change) between two values.
     * <p>
     * The result is returned as a decimal where 1.0 represents a 100% increase
     * and -0.5 represents a 50% decrease. May return a number higher than 1.0 (example: +400% Performance returns 4.0).
     * </p>
     * <b>Special Cases:</b>
     * <ul>
     * <li>If startValue is 0 and endValue > 0, returns 1.0 (100%) as a fallback for infinite growth.</li>
     * <li>If both startValue and endValue are 0, returns 0.0 (0%).</li>
     * </ul>
     *
     * @param startValue The initial value (must be non-negative).
     * @param endValue   The final value (must be non-negative).
     * @return The relative performance as a decimal (e.g., 0.1 for +10%).
     * @throws IllegalArgumentException if either startValue or endValue is negative.
     */
    public static double calculateRelativePerformance(double startValue, double endValue) {
        if (startValue < 0 || endValue < 0) throw new IllegalArgumentException("Values for performance calculation cannot be negative");

        // Prevent division by 0 (If start value is 0)
        if (startValue == 0) {
            // Climbing from 0 to any amount would be an infinitely high performance, hence we return 1 (= 100%)
            // If end value is 0 too, there has been no performance at all, return 0 (= 0%)
            return endValue > 0 ? 1.0 : 0.0;
        }

        // Performance formula: (endValue - startValue) / startValue
        // Returns performance in performance (positive or negative) as a percentage (as a double).
        return (endValue - startValue) / startValue;
    }
}
