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
	public UserDto getUserById(int id) {
		if (id < 0) throw new IllegalArgumentException("User id " + id + "is negative: This is not a valid id.");

		// Search for the user in the db
		UserModel user = userDao.findById(id);

		// Technically redundant, but just to be sure...
		if (user == null) throw new RuntimeException("No user found in database for id " + id);

		// Found a user: Create Dto
		return new UserDto(
				user.getUserId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail()
		);
	}
}
