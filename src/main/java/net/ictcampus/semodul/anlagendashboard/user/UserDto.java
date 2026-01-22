/**
 * Service Data Transfer Object for user information.
 */
package net.ictcampus.semodul.anlagendashboard.user;

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
