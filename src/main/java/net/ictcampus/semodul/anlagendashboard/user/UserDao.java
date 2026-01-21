/**
 * Data Access Object for user-related operations.
 */
package net.ictcampus.semodul.anlagendashboard.user;

public interface UserDao {
	/**
	 * Loads a user by its ID.
	 *
	 * @param id User-ID
	 * @return UserDto or null, if no user was found
	 */
	UserModel findById(int id);
}
