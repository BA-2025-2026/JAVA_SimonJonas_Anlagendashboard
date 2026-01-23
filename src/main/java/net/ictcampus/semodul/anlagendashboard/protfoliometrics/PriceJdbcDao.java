package net.ictcampus.semodul.anlagendashboard.protfoliometrics;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * DAO implementation for PriceModel using JDBC.
 * Provides methods to query prices of an asset relative to a given timestamp.
 */
public class PriceJdbcDao implements PriceDao {

	/**
	 * Retrieves the latest price of an asset that was recorded
	 * at or before the specified timestamp.
	 *
	 * @param timestamp the upper bound timestamp (inclusive)
	 * @param assetId   the ID of the asset
	 * @return PriceModel of the latest price before or at the timestamp,
	 *         or null if no price is found
	 */
	@Override
	public PriceModel getPriceBeforeTimestampByAssetID(LocalDateTime timestamp, int assetId) {

		String sql = """
            SELECT ID_Price, Asset_ID, Timestamp, Price
            FROM price
            WHERE Asset_ID = ? AND Timestamp <= ?
            ORDER BY Timestamp DESC
            LIMIT 1
            """;

		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, assetId);
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(timestamp));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new PriceModel(
							rs.getInt("ID_Price"),
							rs.getInt("Asset_ID"),
							rs.getTimestamp("Timestamp").toLocalDateTime(),
							rs.getDouble("Price")
					);
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error while querying price (before timestamp) for assetID "
							+ assetId + " and timestamp " + timestamp + ".", e);
		}
	}

	/**
	 * Retrieves the earliest price of an asset that was recorded
	 * at or after the specified timestamp.
	 *
	 * @param timestamp the lower bound timestamp (inclusive)
	 * @param assetId   the ID of the asset
	 * @return PriceModel of the earliest price after or at the timestamp,
	 *         or null if no price is found
	 */
	@Override
	public PriceModel getPriceAfterTimestampByAssetID(LocalDateTime timestamp, int assetId) {

		String sql = """
            SELECT ID_Price, Asset_ID, Timestamp, Price
            FROM price
            WHERE Asset_ID = ? AND Timestamp >= ?
            ORDER BY Timestamp ASC
            LIMIT 1
            """;

		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, assetId);
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(timestamp));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new PriceModel(
							rs.getInt("ID_Price"),
							rs.getInt("Asset_ID"),
							rs.getTimestamp("Timestamp").toLocalDateTime(),
							rs.getDouble("Price")
					);
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error while querying price (after timestamp) for assetID "
							+ assetId + " and timestamp " + timestamp + ".", e);
		}
	}

	public static void main(String[] args) {
		// Create an instance of PriceJdbcDao
		PriceJdbcDao priceDao = new PriceJdbcDao();

		// Test data: asset ID and timestamp
		int testAssetId = 1;
		LocalDateTime testTimestamp = LocalDateTime.of(2024, 1, 15, 12, 0);

		System.out.println("Testing PriceJdbcDao");
		System.out.println("===================");
		System.out.println("Test Asset ID: " + testAssetId);
		System.out.println("Test Timestamp: " + testTimestamp);
		System.out.println();

		// Test getPriceBeforeTimestampByAssetID
		try {
			System.out.println("Testing getPriceBeforeTimestampByAssetID:");
			PriceModel priceBefore = priceDao.getPriceBeforeTimestampByAssetID(testTimestamp, testAssetId);
			if (priceBefore != null) {
				System.out.println("Found price before timestamp:");
				System.out.println("  Price ID: " + priceBefore.getPrice());
				System.out.println("  Asset ID: " + priceBefore.getAssetId());
				System.out.println("  Timestamp: " + priceBefore.getTimestamp());
				System.out.println("  Price: " + priceBefore.getPrice());
			} else {
				System.out.println("No price found before timestamp.");
			}
		} catch (RuntimeException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println();

		// Test getPriceAfterTimestampByAssetID
		try {
			System.out.println("Testing getPriceAfterTimestampByAssetID:");
			PriceModel priceAfter = priceDao.getPriceAfterTimestampByAssetID(testTimestamp, testAssetId);
			if (priceAfter != null) {
				System.out.println("Found price after timestamp:");
				System.out.println("  Price ID: " + priceAfter.getPrice());
				System.out.println("  Asset ID: " + priceAfter.getAssetId());
				System.out.println("  Timestamp: " + priceAfter.getTimestamp());
				System.out.println("  Price: " + priceAfter.getPrice());
			} else {
				System.out.println("No price found after timestamp.");
			}
		} catch (RuntimeException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

}
