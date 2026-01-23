/**
 * JDBC implementation of the UserAssetDao interface.
 */

package net.ictcampus.semodul.anlagendashboard.protfoliometrics;

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
							rs.getInt("Quantity"),
							rs.getTimestamp("PurchasedAt").toLocalDateTime(),
							null // SoldAt ist bei offenen Positionen immer null
					));
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error while loading open positions for user with ID " + userId, e);
		}

		return openPositions;
	}

	public static void main(String[] args) {
		// Create an instance of the DAO
		UserAssetJdbcDao dao = new UserAssetJdbcDao();

		// Test with user ID 1 (adjust based on your test data)
		int testUserId = 1;

		System.out.println("Testing getOpenPositionsByUserId() with User ID: " + testUserId);
		System.out.println("-----------------------------------------------------------");

		try {
			List<UserAssetModel> openPositions = dao.getOpenPositionsByUserId(testUserId);

			if (openPositions.isEmpty()) {
				System.out.println("No open positions found for user ID " + testUserId);
			} else {
				System.out.println("Found " + openPositions.size() + " open position(s):");
				for (UserAssetModel position : openPositions) {
					System.out.println("\nPosition Details:");
					System.out.println("  ID: " + position.getAssetId());
					System.out.println("  User ID: " + position.getUserId());
					System.out.println("  Asset ID: " + position.getAssetId());
					System.out.println("  Broker ID: " + position.getBrokerId());
					System.out.println("  Quantity: " + position.getQuantity());
					System.out.println("  Purchased At: " + position.getPurchasedAt());
					System.out.println("  Sold At: " + position.getSoldAt());
				}
			}
		} catch (RuntimeException e) {
			System.err.println("Error occurred while testing: " + e.getMessage());
			e.printStackTrace();
		}
	}

}

