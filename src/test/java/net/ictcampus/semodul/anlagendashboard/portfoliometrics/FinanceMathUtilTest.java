package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.utility.FinanceMathUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test class for the FinanceMathUtil utility class.
 * This class contains test cases for the methods provided by FinanceMathUtil
 * to ensure correctness of absolute and relative performance calculations.
 * It includes tests for both regular usage scenarios and edge cases where exceptions
 * may be expected.
 */

public class FinanceMathUtilTest {

	/**
	 * Tests that the {@code calculateAbsolutePerformance} method throws an exception if the start or the end value is negative.
	 */
	@Test
	public void calculateAbsolutePerformance_negativeValue_throwsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> FinanceMathUtil.calculateAbsolutePerformance(-100, 50));
	}

	/**
	 * Tests that the {@code calculateAbsolutePerformance} method returns the correct value for positive start and end values.
	 */
	@Test
	public void calculateAbsolutePerformance_positiveValues_returnsCorrectValue() {
		double result = FinanceMathUtil.calculateAbsolutePerformance(100, 50);
		assert(result == -50);
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method throws an exception if the start value is negative.
	 */
	@Test
	public void calculateRelativePerformance_negativeValues_throwsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> FinanceMathUtil.calculateRelativePerformance(-100, 50));
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method returns the correct value for positive start and end values.
	 */
	@Test
	public void calculateRelativePerformance_startValueSmallerThanEndValue_returnsCorrectValue() {
		double result = FinanceMathUtil.calculateRelativePerformance(50, 100);
		assert(result == 1);
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method throws an exception if the start value is zero.
	 */
	@Test
	public void calculateRelativePerformance_startValueZero_throwsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> FinanceMathUtil.calculateRelativePerformance(0, 0));
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method returns the correct value for positive start and end values.
	 */
	@Test
	public void calculateRelativePerformance_endValueZero_returnsZero() {
		double result = FinanceMathUtil.calculateRelativePerformance(100, 0);
		assert(result == -1);
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method returns the correct value when start and end values are equal.
	 */
	@Test
	public void calculateRelativePerformance_startValueEqualsEndValue_returnsZero() {
		double result = FinanceMathUtil.calculateRelativePerformance(100, 100);
		assert(result == 0);
	}

	/**
	 * Tests that the {@code calculateRelativePerformance} method returns the correct value when start and end values are equal or end value approaches zero.
	 */
	@Test
	public void calculateRelativePerformance_startValueGreaterThanEndValue_returnsNegativeOne() {
		double result = FinanceMathUtil.calculateRelativePerformance(100, 50);
		assert(result == -0.5);
	}
}
