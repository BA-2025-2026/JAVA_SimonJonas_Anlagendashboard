package net.ictcampus.semodul.anlagendashboard.user;

import net.ictcampus.semodul.anlagendashboard.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC-based Data Access Object for user-related operations.
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
						rs.getString("Email"),
						rs.getString("FirstName"),
						rs.getString("LastName"),
						rs.getString("Password")
				);
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new RuntimeException("Error from database while trying to get user data for user with ID " + id, e);
		}
	}
}
