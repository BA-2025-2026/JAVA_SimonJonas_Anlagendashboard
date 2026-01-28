/**
 * JDBC implementation of the UserAssetDao interface.
 */

package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAssetJdbcDao implements UserAssetDao {

	/**
	 * Retrieves the open positions for a user based on their user ID.
	 * @param userId The ID of the user for whom to retrieve open positions.
	 * @return List of UserAssetModel objects containing the open positions for the specified user.
	 */
	@Override
	public List<UserAssetModel> getOpenPositionsByUserId(int userId) {
		List<UserAssetModel> openPositions = new ArrayList<>();

		String sql = """
        SELECT ID_User_Asset, User_ID, Asset_ID, Broker_ID, Quantity, PurchasedAt
        FROM user_asset
        WHERE User_ID = ? AND SoldAt IS NULL
        """;

		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					openPositions.add(new UserAssetModel(
							rs.getInt("ID_User_Asset"),
							rs.getInt("User_ID"),
							rs.getInt("Asset_ID"),
							rs.getInt("Broker_ID"),
							rs.getDouble("Quantity"),
							rs.getTimestamp("PurchasedAt").toLocalDateTime(),
							null // SoldAt ist bei offenen Positionen immer null
					));
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Database error while loading open positions for user with ID " + userId, e);
		}

		return openPositions;
	}
}

