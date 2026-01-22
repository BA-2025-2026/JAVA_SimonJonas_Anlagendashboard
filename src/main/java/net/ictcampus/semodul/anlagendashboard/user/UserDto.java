/**
 * Service Data Transfer Object for user information.
 */
package net.ictcampus.semodul.anlagendashboard.user;

/**
 * Data transfer object used to send user information out through the api.
 * Leaves out sensitive information that should never be sent out, like the password hash or others.
 * Use this UserDto to fill user data into it after the service completes it's work.
 * Then hand this UserDto to the controller to process the data.
 */
public class UserDto {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;

	public UserDto(int userId, String firstName, String lastName, String email) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
}
