package net.ictcampus.semodul.anlagendashboard.portfoliometrics;

import net.ictcampus.semodul.anlagendashboard.user.UserController;
import net.ictcampus.semodul.anlagendashboard.user.UserDao;
import net.ictcampus.semodul.anlagendashboard.user.UserJdbcDao;
import net.ictcampus.semodul.anlagendashboard.user.UserService;

public class TestClassST07 {
	public static void main(String[] args) {
		UserDao userDao = new UserJdbcDao();
		UserService userService = new UserService(userDao);
		UserController userController = new UserController(userService);
		userController.getUserByIdEndpoint(-1);
	}
}
