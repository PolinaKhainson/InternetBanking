package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Update settings command.
 * 
 * @author P.Khainson
 * 
 */
public class UpdateSettingsCommand extends Command {

	private static final long serialVersionUID = 7775113210192353617L;
	private static final Logger LOG = Logger
			.getLogger(UpdateSettingsCommand.class);
	private Map<String, String> errors = new HashMap<String, String>();
	private static final String PASSWORD = "password";
	private static final String PASSWORD2 = "password2";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Get session from request: session --> " + session);

		// obtain user parameters from session
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);

		// obtain user parameters from request
		String password = request.getParameter(PASSWORD);
		LOG.trace("Request parameter: password --> " + password);

		String password2 = request.getParameter(PASSWORD2);
		LOG.trace("Request parameter: password2 --> " + password2);

		String firstName = request.getParameter(FIRST_NAME);
		LOG.trace("Request parameter: firstName --> " + firstName);

		String lastName = request.getParameter(LAST_NAME);
		LOG.trace("Request parameter: lastName --> " + lastName);
		// validate password
		String updatePassword = null;
		if (validatePassword(password, password2) && password != null
				&& !password.isEmpty()) {
			user.setPassword(password);
			updatePassword = "Update of password was succesful";
		}
		// validate first name
		String updateFirstName = null;
		if (firstName != null && !firstName.isEmpty()
				&& validateFirstName(firstName)) {
			user.setFirstName(firstName);
			updateFirstName = "Update of first name was succesful";
		}
		// validate last name
		String updateLastName = null;
		if (lastName != null && !lastName.isEmpty()
				&& validateLastName(lastName)) {
			user.setLastName(lastName);
			updateLastName = "Update of last name was succesful";
		}
		if (!errors.isEmpty()) {
			// if validation of some fields failed, set corresponding error to
			// request attribute
			request.setAttribute("errors", errors);
			LOG.trace("Set the request attribute:  errors --> " + errors);
			request.setAttribute(PASSWORD, password);
			LOG.trace("Set the request attribute:  password --> " + password);
			request.setAttribute(PASSWORD2, password2);
			LOG.trace("Set the request attribute: password2 --> " + password2);
			request.setAttribute(FIRST_NAME, firstName);
			LOG.trace("Set the request attribute: firstName --> " + firstName);
			request.setAttribute(LAST_NAME, lastName);
			LOG.trace("Set the request attribute: lastName --> " + lastName);
		} else {
			// if filled fields are valid - update user
			DBManager manager = DBManager.getInstance();
			manager.updateUser(user);
			LOG.trace("Update user in DB: user --> " + user);

			session.setAttribute("user", user);
			LOG.trace("Set the session attribute: user --> " + user);
		}

		// set messages to request if update of some fields was successful
		if (updatePassword != null) {
			request.setAttribute("updatePassword", updatePassword);
			LOG.trace("Set the request attribute: updatePassword --> "
					+ updatePassword);
		}
		if (updateFirstName != null) {
			request.setAttribute("updateFirstName", updateFirstName);
			LOG.trace("Set the request attribute: updateFirstName --> "
					+ updateFirstName);
		}
		if (updateLastName != null) {
			request.setAttribute("updateLastName", updateLastName);
			LOG.trace("Set the request attribute: updateLastName --> "
					+ updateLastName);
		}
		// end
		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}

	/**
	 * Validates passwords identity
	 * 
	 * @param password
	 *            String first password value
	 * @param password2
	 *            String second password value
	 * 
	 * @return boolean true if passwords are same, else - returns false
	 * 
	 */
	private boolean validatePassword(String password, String password2) {
		errors.clear();
		boolean result = true;
		if (password != null && !password.isEmpty()) {
			Pattern pattern = Pattern
					.compile("^[a-zA-Z0-9\u0400-\u04FF!@$%^&*]{4,10}");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.matches()) {
				errors.put(PASSWORD, Messages.ERR_INVALID_PASSWORD);
				LOG.trace("Password validation: error message --> "
						+ Messages.ERR_INVALID_PASSWORD);
				result = false;
			} else {
				if (password2 == null || password2.isEmpty()) {
					errors.put(PASSWORD2, Messages.ERR_SACOND_PASSWORD_IS_EMPTY);
					LOG.trace("Password2 validation: error message --> "
							+ Messages.ERR_SACOND_PASSWORD_IS_EMPTY);

					result = false;
				}
			}
		}

		if (password2 != null && !password2.isEmpty()) {
			Pattern pattern = Pattern
					.compile("^[a-zA-Z\u0400-\u04FF0-9!@$%^&*]{4,10}");
			Matcher matcher = pattern.matcher(password2);
			if (!matcher.matches()) {
				errors.put(PASSWORD2, Messages.ERR_INVALID_PASSWORD);
				LOG.trace("Password2 validation: error message --> "
						+ Messages.ERR_INVALID_PASSWORD);
				result = false;
			} else {
				if (password == null || password.isEmpty()) {
					errors.put(PASSWORD, Messages.ERR_FIRST_PASSWORD_IS_EMPTY);
					LOG.trace("Password validation: error message --> "
							+ Messages.ERR_FIRST_PASSWORD_IS_EMPTY);
					result = false;
				}

			}

		}
		if (password != null && password2 != null
				&& !password.equals(password2)) {
			errors.put(PASSWORD2, Messages.ERR_PASSWORDS_NOT_THE_SAME);
			LOG.trace("Password2 validation: error message --> "
					+ Messages.ERR_PASSWORDS_NOT_THE_SAME);
			result = false;
		}
		return result;
	}

	/**
	 * Validates first name value
	 * 
	 * @param firstName
	 *            String first name
	 */
	private boolean validateFirstName(String firstName) {
		if (firstName != null && !firstName.isEmpty()) {
			Pattern pattern = Pattern.compile("[a-zA-Z\u0400-\u04FF ]{1,19}");
			Matcher matcher = pattern.matcher(firstName);
			if (!matcher.matches()) {
				errors.put(FIRST_NAME, Messages.ERR_INVALID_FIRST_NAME);
				LOG.trace("First name validation: error message --> "
						+ Messages.ERR_INVALID_FIRST_NAME);
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates last name value
	 * 
	 * @param lastName
	 *            String last name
	 */
	private boolean validateLastName(String lastName) {
		if (lastName != null && !lastName.isEmpty()) {
			Pattern pattern = Pattern.compile("[a-zA-Z\u0400-\u04FF ]{1,19}");
			Matcher matcher = pattern.matcher(lastName);
			if (!matcher.matches()) {
				errors.put(LAST_NAME, Messages.ERR_INVALID_LAST_NAME);
				LOG.trace("Last name validation: error message --> "
						+ Messages.ERR_INVALID_LAST_NAME);
				return false;
			}
		}

		return true;
	}

}
