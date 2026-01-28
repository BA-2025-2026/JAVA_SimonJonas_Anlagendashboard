package net.ictcampus.semodul.anlagendashboard.user;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;
import net.ictcampus.semodul.anlagendashboard.utility.NoDataFoundException;

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
		String sql = "SELECT ID_User, Email, FirstName, LastName, " +
				"Password FROM user WHERE ID_User = ?";
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
				throw new NoDataFoundException("No user found in database for id " + id);
			}

		} catch (SQLException e) {
			throw new RuntimeException(
					"Database error from database while trying to get user data for user with ID " + id +
							". " + e.getMessage(), e);
		}
	}

}
