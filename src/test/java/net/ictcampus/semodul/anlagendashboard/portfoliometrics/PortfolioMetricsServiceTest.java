package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;
import net.ictcampus.semodul.anlagendashboard.portfoliometrics.PriceDao;
import net.ictcampus.semodul.anlagendashboard.portfoliometrics.UserAssetDao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@code PortfolioMetricsService} class.
 * This test class validates the behavior of the portfolio metrics functionality
 * under various scenarios using mocked data sources.
 */

public class PortfolioMetricsServiceTest {

	/**
	 * A fake implementation of the {@code UserAssetDao} interface designed for testing purposes.
	 * This implementation provides a predefined list of open user asset positions for a given user ID.
	 */
	private static class FakeUserAssetDao implements UserAssetDao {
		@Override
		public List<UserAssetModel> getOpenPositionsByUserId(int userId) {

			List<UserAssetModel> openPositions = new ArrayList<>();

			openPositions.add(new UserAssetModel(
					1,
					userId,
					101,
					1,
					2.0,
					LocalDateTime.of(2024, 1, 1, 10, 0),
					null
			));

			openPositions.add(new UserAssetModel(
					2,
					userId,
					102,
					1,
					5.0,
					LocalDateTime.of(2024, 2, 1, 10, 0),
					null
			));

			openPositions.add(new UserAssetModel(
					3,
					userId,
					103,
					2,
					1.5,
					LocalDateTime.of(2024, 3, 1, 10, 0),
					null
			));

			return openPositions;
		}
	}

	/**
	 * A fake implementation of the {@code PriceDao} interface designed for testing purposes.
	 * This implementation provides a fixed price of 100.0 for any asset at any timestamp.
	 */
	private static class FakePriceDao implements PriceDao {

		@Override
		public PriceModel getPriceBeforeTimestampByAssetID(
				LocalDateTime timestamp, int assetId) {

			return new PriceModel(
					1,
					assetId,
					timestamp,
					100.0   //imaginary Price
			);
		}

		@Override
		public PriceModel getPriceAfterTimestampByAssetID(
				LocalDateTime timestamp, int assetId) {

			return null;    // reicht für unseren Test
		}
	}

	/**
	 * A fake implementation of the {@code UserAssetDao} interface designed for testing purposes.
	 */

	private static class FakeUserAssetDaoEmpty implements UserAssetDao {
		@Override
		public List<UserAssetModel> getOpenPositionsByUserId(int userId) {
			return new ArrayList<>();
		}
	}

	/**
	 * Tests that an {@code IllegalArgumentException} is thrown when an invalid user ID is provided to the {@code getPortfolioMetricsByUserId} method.
	 */

	@Test
	void getPortfolioMetricsByUserId_negativeUserId_throwsIllegalArgumentException() {
		PortfolioMetricsService service = new PortfolioMetricsService(new FakePriceDao(), new FakeUserAssetDao());
		assertThrows(IllegalArgumentException.class, () -> service.getPortfolioMetricsByUserId(-1));
	}

	/**
	 * Tests that the {@code getPortfolioMetricsByUserId} method returns zero metrics when no open positions are found for the given user ID.
	 */

	@Test
	void getPortfolioMetricsByUserId_withThreeOpenPositions_returnsCorrectMetrics() {
		int userId = 1;
		PortfolioMetricsService service = new PortfolioMetricsService(new FakePriceDao(), new FakeUserAssetDao());

		PortfolioMetricsDto dto = service.getPortfolioMetricsByUserId(userId);

		assertEquals(850, dto.getTotalPortfolioValue());
		assertEquals(850, dto.getInvestedTotalValue());
		assertEquals(0.0, dto.getAbsolutePerformance());
		assertEquals(0.0, dto.getRelativePerformance());
	}

	/**
	 * Tests that the {@code getPortfolioMetricsByUserId} method returns zero metrics when no open positions are found for the given user ID.
	 */

	@Test
	void getPortfolioMetricsByUserId_noOpenPositions_returnsZeroMetrics() {
		// Arrange
		int userId = 7;
		PortfolioMetricsService service = new PortfolioMetricsService(
				new FakePriceDao(),
				new FakeUserAssetDaoEmpty()
		);

		// Act
		PortfolioMetricsDto dto = service.getPortfolioMetricsByUserId(userId);

		// Assert
		assertEquals(userId, dto.getUserId());
		assertEquals(0.0, dto.getTotalPortfolioValue());
		assertEquals(0.0, dto.getInvestedTotalValue());
		assertEquals(0.0, dto.getAbsolutePerformance());
		assertEquals(0.0, dto.getRelativePerformance());

	}
}
