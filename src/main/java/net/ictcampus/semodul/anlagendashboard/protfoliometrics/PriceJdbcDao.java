package net.ictcampus.semodul.anlagendashboard.protfoliometrics;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;
import net.ictcampus.semodul.anlagendashboard.user.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PriceJdbcDao implements PriceDao {

	@Override
	public PriceModel getPriceBeforeTimestampByAssetID(LocalDateTime timestamp, int assetId) {
		String sql = "SELECT * FROM price WHERE Asset_ID = ? AND Timestamp <= ?ORDER BY Timestamp DESC LIMIT 1";

		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, assetId);
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(timestamp));

			ResultSet rs = ps.executeQuery();

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

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error from database while trying to get price data with assetID " + assetId + " and timestamp " + timestamp + ". " + e.getMessage(), e);
		}

	}

	@Override
	public PriceModel getPriceAfterTimestampByAssetID(LocalDateTime timestamp, int assetId) {
		String sql = "SELECT * FROM price WHERE Asset_ID = ? AND Timestamp <= ?ORDER BY Timestamp DESC LIMIT 1";

		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, assetId);
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(timestamp));

			ResultSet rs = ps.executeQuery();

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

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error from database while trying to get price data with assetID " + assetId + " and timestamp " + timestamp + ". " + e.getMessage(), e);
		}
	}
}