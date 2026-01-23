package net.ictcampus.semodul.anlagendashboard.user;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC-based Database Access Object for user-related operations.
 */

public class UserJdbcDao implements UserDao {
	/**
	 * Loads userdata selected by its ID into a new UserModel object.
	 *
	 * @param id User-ID
	 * @return UserModel object containing user data or null if user not found
	 */
	@Override
	public UserModel findById(int id) {
		String sql = "SELECT ID_User, Email, FirstName, LastName, Password FROM user WHERE ID_User = ?";
		try (Connection con = ConnectionFactory.getInstance().getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new UserModel(
						rs.getInt("ID_User"),
						rs.getString("FirstName"),
						rs.getString("LastName"),
						rs.getString("Email"),
						rs.getString("Password")
				);
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Error from database while trying to get user data for user with ID " + id + ". " + e.getMessage(), e);
		}
	}
	public static void main(String[] args) {
		UserJdbcDao userDao = new UserJdbcDao();

		// Test case 1: Try to find a user with ID 1
		System.out.println("Testing findById(1):");
		try {
			UserModel user1 = userDao.findById(1);
			if (user1 != null) {
				System.out.println("User found: " + user1.getFirstName() + " " + user1.getLastName() + " (" + user1.getEmail() + ")");
			} else {
				System.out.println("User with ID 1 not found.");
			}
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}

		// Test case 2: Try to find a user with ID 2
		System.out.println("\nTesting findById(2):");
		try {
			UserModel user2 = userDao.findById(2);
			if (user2 != null) {
				System.out.println("User found: " + user2.getFirstName() + " " + user2.getLastName() + " (" + user2.getEmail() + ")");
			} else {
				System.out.println("User with ID 2 not found.");
			}
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}

		// Test case 3: Try to find a user with non-existent ID 999
		System.out.println("\nTesting findById(999):");
		try {
			UserModel user999 = userDao.findById(999);
			if (user999 != null) {
				System.out.println("User found: " + user999.getFirstName() + " " + user999.getLastName() + " (" + user999.getEmail() + ")");
			} else {
				System.out.println("User with ID 999 not found.");
			}
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
