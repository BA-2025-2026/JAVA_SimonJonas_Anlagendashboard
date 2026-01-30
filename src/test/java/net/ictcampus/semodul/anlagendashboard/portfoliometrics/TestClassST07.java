package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.user.UserController;
import net.ictcampus.semodul.anlagendashboard.user.UserDao;
import net.ictcampus.semodul.anlagendashboard.user.UserJdbcDao;
import net.ictcampus.semodul.anlagendashboard.user.UserService;

/**
 * Manual integration test class for the User endpoint.
 * <p>
 * This class validates the connectivity between the {@link UserController},
 * {@link UserService}, and the {@link UserJdbcDao}. We test here a
 * "happy path" or error handling check by trying to retrieve a user
 * with an invalid ID (-1) through the full stack.
 */
public class TestClassST07 {
	public static void main(String[] args) {
		UserDao userDao = new UserJdbcDao();
		UserService userService = new UserService(userDao);
		UserController userController = new UserController(userService);
		userController.getUserByIdEndpoint(-1);
	}
}
