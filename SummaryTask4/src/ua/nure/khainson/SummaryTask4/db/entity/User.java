package ua.nure.khainson.SummaryTask4.db.entity;

/**
 * User entity.
 * 
 * @author P.Khainson
 * 
 */
public class User extends Entity {

	private static final long serialVersionUID = -2140627779707576349L;

	private int lockStatusId;

	private int roleId;

	private String login;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	public int getLockStatusId() {
		return lockStatusId;
	}

	public void setLockStatusId(int lockStatusId) {
		this.lockStatusId = lockStatusId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [lockStatusId=" + lockStatusId + ", roleId=" + roleId
				+ ", login=" + login + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}

}
