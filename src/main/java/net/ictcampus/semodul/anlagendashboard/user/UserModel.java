/**
 * Model class representing user data.
 */
package net.ictcampus.semodul.anlagendashboard.user;

/**
 * Models the table "user" of the database. With this UserModel we can store user data that we receive from the database,
 * in the RAM of our application for further processing.
 */
public class UserModel {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;

	public UserModel(int userId, String firstName, String lastName, String email, String passwordHash) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public int getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
}
