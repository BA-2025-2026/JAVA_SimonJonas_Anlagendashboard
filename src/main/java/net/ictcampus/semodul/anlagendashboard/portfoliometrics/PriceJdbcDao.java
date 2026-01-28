package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

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
					"Database error while querying price (before timestamp) for assetID "
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
					"Database error while querying price (after timestamp) for assetID "
							+ assetId + " and timestamp " + timestamp + ".", e);
		}
	}
}
