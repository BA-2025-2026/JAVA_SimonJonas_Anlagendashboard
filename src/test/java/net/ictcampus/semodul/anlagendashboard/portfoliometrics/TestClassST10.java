package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the {@link PortfolioMetricsController} focused on error resilience.
 * <p>
 * This class uses static inner "Fake" classes to simulate specific infrastructure
 * behaviors, such as database failures. It ensures that the controller layer
 * correctly catches exceptions and returns a formatted JSON error response
 * rather than leaking stack traces or partial data.
 */
public class TestClassST10 {

	/**
	 * A simulated Data Access Object that always throws a {@link RuntimeException}.
	 * Used to test how the service and controller handle unexpected DAO failures.
	 */
	private static class FakeUserAssetDaoError implements UserAssetDao {
		@Override
		public List<UserAssetModel> getOpenPositionsByUserId(int userId) {
			throw new RuntimeException(
					"Simulated DAO failure while loading open positions for user with ID " + userId
			);
		}
	}

	/**
	 * A minimal implementation of {@link PriceDao} that performs no operations.
	 * Used as a stub where price data is required but not the focus of the test.
	 */
	private static class FakePriceDaoNoOp implements PriceDao {
		@Override
		public PriceModel getPriceBeforeTimestampByAssetID(LocalDateTime timestamp, int assetId) {
			return null;
		}

		@Override
		public PriceModel getPriceAfterTimestampByAssetID(LocalDateTime timestamp, int assetId) {
			return null;
		}
	}

	/**
	 * Verifies that when the DAO fails, the controller prints a valid JSON error
	 * response to the console.
	 * <p>
	 * The test checks that:
	 * <ul>
	 * <li>The response header is present.</li>
	 * <li>The specific error message from the DAO is included.</li>
	 * <li>Standard JSON error fields (message, timestamp) are present.</li>
	 * <li>No metrics data or stack traces are leaked to the output.</li>
	 * </ul>
	 */
	@Test
	void getPortfolioMetricsByUserIdEndpoint_whenDaoFails_printsErrorJsonWithoutStacktraceOrMetrics() {
		int userId = 2;

		PortfolioMetricsService service =
				new PortfolioMetricsService(new FakePriceDaoNoOp(), new FakeUserAssetDaoError());
		PortfolioMetricsController controller = new PortfolioMetricsController(service);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(buffer));

		try {
			controller.getPortfolioMetricsByUserIdEndpoint(userId);
		} finally {
			System.setOut(originalOut);
		}

		String output = buffer.toString(StandardCharsets.UTF_8).trim();

		// Header from controller
		assertTrue(
				output.contains("==== RESPONSE of GET /portfoliometrics/{id} ===="),
				"Header should be printed"
		);

		// Error JSON: message from RuntimeException + ErrorDto fields
		assertTrue(
				output.contains("Simulated DAO failure while loading open positions for user with ID " + userId),
				"Error message from DAO should be printed"
		);
		assertTrue(
				output.contains("\"message\""),
				"Error JSON should contain 'message' field"
		);
		assertTrue(
				output.contains("\"timestamp\""),
				"Error JSON should contain 'timestamp' field"
		);

		// No PortfolioMetricsDto JSON (typical field)
		assertFalse(
				output.contains("totalPortfolioValue"),
				"No PortfolioMetricsDto JSON should be printed"
		);

		// No stacktrace
		assertFalse(
				output.contains("java.lang.RuntimeException"),
				"No stacktrace should be printed"
		);
	}
}