package net.ictcampus.semodul.anlagendashboard.user;

/**
 * Service class for user-related operations.
 * Orchestrates access to the DAO and maps UserModel to UserDto.
 */
public class UserService {

	private final UserDao userDao;

	/**
	 * Service depends only on the UserDao interface, not the specific implementation via JDBC.
	 */
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Loads a user by ID and returns a DTO without sensitive data (no password).
	 *
	 * @param id ID of the user
	 * @return UserDto or null if no user with this ID exists
	 */
	public UserDto getUserDto(int id) {
		if (id > 0) {
			UserModel user = userDao.findById(id);

			if (user == null) {
				return null; // no user found → caller decides how to handle it
			}

			return new UserDto(
					user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail()
			);
		} else {
			throw new IllegalArgumentException("Invalid user ID: " + id);
		}

	}
}
