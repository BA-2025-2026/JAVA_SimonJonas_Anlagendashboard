/**
 * Interface for accessing user asset data.
 */
package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import java.util.List;

public interface UserAssetDao {
	/**
	 * Retrieves the open positions for a user based on their user ID.
	 * @param userId The ID of the user for whom to retrieve open positions.
	 * @return The userAssetModel containing the open positions for the specified user.
	 */
	List<UserAssetModel> getOpenPositionsByUserId(int userId);
}
