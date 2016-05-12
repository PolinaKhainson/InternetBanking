package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.LockStatus;
import ua.nure.khainson.SummaryTask4.db.Role;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.DBException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

public class RegistrateUserCommand extends Command {

	private static final long serialVersionUID = -8675901695343169625L;
	private static final Logger LOG = Logger
			.getLogger(RegistrateUserCommand.class);
	private Map<String, String> errors = new HashMap<String, String>();
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String PASSWORD2 = "password2";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL = "email";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();
		// obtain user parameters from request
		String firstName = request.getParameter(FIRST_NAME);
		LOG.trace("Request parameter: firstName --> " + firstName);

		String lastName = request.getParameter(LAST_NAME);
		LOG.trace("Request parameter: lastName --> " + lastName);

		String login = request.getParameter(LOGIN);
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter(PASSWORD);
		LOG.trace("Request parameter: password --> " + password);

		String password2 = request.getParameter(PASSWORD2);
		LOG.trace("Request parameter: password --> " + password2);

		String email = request.getParameter(EMAIL);
		LOG.trace("Request parameter: password --> " + email);

		String forward = Path.PAGE_ERROR_PAGE;
		// validate all user parameters
		validateFields(firstName, lastName, login, password, password2, email);

		if (!errors.isEmpty()) {
			// set errors to request attribute if validation failed
			request.setAttribute("errors", errors);
			LOG.trace("Request attribute: errors --> " + errors);
			request.setAttribute(PASSWORD, password);
			LOG.trace("Request attribute: password --> " + password);
			request.setAttribute(PASSWORD2, password2);
			LOG.trace("Request attribute:  password2 --> " + password2);
			request.setAttribute(FIRST_NAME, firstName);
			LOG.trace("Request attribute: firstName --> " + firstName);
			request.setAttribute(LAST_NAME, lastName);
			LOG.trace("Request attribute: lastName --> " + lastName);
			request.setAttribute(LOGIN, login);
			LOG.trace("Request attribute: login --> " + login);
			request.setAttribute(EMAIL, email);
			LOG.trace("Request attribute: email --> " + email);
			// go back to registration page
			forward = Path.PAGE_REGISTRATION;
		} else {
			// if validation of parameters is successful, create user by
			// parameters
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setLockStatusId(LockStatus.UNLOCKED.ordinal());
			user.setLogin(login);
			user.setPassword(password);
			user.setEmail(email);
			user.setRoleId(Role.CLIENT.ordinal());
			LOG.trace("Create user --> " + user);
			// insert created user to DB
			manager.insertUser(user);
			LOG.trace("Insert in DB --> user" + user);
			// set users first name and last name to request attributes
			request.setAttribute(FIRST_NAME, user.getFirstName());
			LOG.trace("Set the request attribute: firstName --> "
					+ user.getFirstName());
			request.setAttribute(LAST_NAME, user.getLastName());
			LOG.trace("Set the request attribute: lastName --> "
					+ user.getLastName());
			// froward to Successful registration page
			forward = Path.PAGE_SUCCESSFUL_REGISTRATION;
			LOG.trace("Forward address --> " + forward);
		}

		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validates all user fields (firstName, lastName, login, password,
	 * password2, email)
	 * 
	 * @param firstName
	 *            String first name value
	 * @param lastName
	 *            String last name value
	 * @param login
	 *            String login value
	 * @param password
	 *            String first password value
	 * @param password2
	 *            String second password value
	 * @param email
	 *            String email value
	 * 
	 * @return boolean true if all fields are valid, else - returns false
	 * @throws DBException
	 * 
	 */
	private boolean validateFields(String firstName, String lastName,
			String login, String password, String password2, String email)
			throws DBException {
		errors.clear();
		final String validError = " validation error --> errorMessage";
		final String passwordError = "Password" + validError;
		final String password2Error = "Password2" + validError;
		final String firstNameError = "First name" + validError;
		final String lastNameError = "Last name" + validError;
		final String loginError = "Login" + validError;
		final String emailError = "Email" + validError;
		boolean result = true;
		if (password != null && !password.isEmpty()) {
			Pattern pattern = Pattern
					.compile("^[a-zA-Z\u0400-\u04FF0-9!@$%^&*]{4,10}");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.matches()) {
				errors.put(PASSWORD, Messages.ERR_INVALID_PASSWORD);
				LOG.debug(passwordError
						+ Messages.ERR_INVALID_PASSWORD);

				result = false;
			} else {
				if (password2 == null || password2.isEmpty()) {
					errors.put(PASSWORD2, Messages.ERR_SACOND_PASSWORD_IS_EMPTY);
					LOG.debug(password2Error
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
				LOG.debug(password2Error
						+ Messages.ERR_INVALID_PASSWORD);
				result = false;
			} else {
				if (password == null || password.isEmpty()) {
					errors.put(PASSWORD, Messages.ERR_FIRST_PASSWORD_IS_EMPTY);
					LOG.debug(passwordError
							+ Messages.ERR_FIRST_PASSWORD_IS_EMPTY);
					result = false;
				}

			}

		}
		if (password != null && password2 != null
				&& !password.equals(password2)) {
			errors.put(PASSWORD2, Messages.ERR_PASSWORDS_NOT_THE_SAME);
			LOG.debug(password2Error
					+ Messages.ERR_PASSWORDS_NOT_THE_SAME);
			result = false;
		}
		if (password != null && password2 != null &&
				password.isEmpty() && password2.isEmpty()) {
			errors.put(PASSWORD, Messages.ERR_PASSWORD_IS_EMPTY);
			LOG.debug(password2Error
					+ Messages.ERR_PASSWORD_IS_EMPTY);
			errors.put(PASSWORD2, Messages.ERR_PASSWORD_IS_EMPTY);
			result = false;
			LOG.debug(password2Error
					+ Messages.ERR_PASSWORD_IS_EMPTY);

		}

		if (firstName != null && !firstName.isEmpty()) {
			Pattern pattern = Pattern.compile("[a-zA-Z\u0400-\u04FF ]{1,19}");
			Matcher matcher = pattern.matcher(firstName);
			if (!matcher.matches()) {
				errors.put(FIRST_NAME, Messages.ERR_INVALID_FIRST_NAME
						+ firstName);
				LOG.debug(firstNameError
						+ Messages.ERR_INVALID_FIRST_NAME);
				result = false;
			}
		} else {
			errors.put(FIRST_NAME, Messages.ERR_FIRST_NAME_IS_EMPTY);
			LOG.debug(firstNameError
					+ Messages.ERR_FIRST_NAME_IS_EMPTY);
			result = false;
		}
		if (lastName != null && !lastName.isEmpty()) {
			Pattern pattern = Pattern.compile("[a-zA-Z\u0400-\u04FF ]{1,19}");
			Matcher matcher = pattern.matcher(lastName);
			if (!matcher.matches()) {
				errors.put(LAST_NAME, Messages.ERR_INVALID_LAST_NAME);
				LOG.debug(lastNameError
						+ Messages.ERR_INVALID_LAST_NAME);
				result = false;
			}
		} else {
			errors.put(LAST_NAME, Messages.ERR_LAST_NAME_IS_EMPTY);
			LOG.debug(lastNameError
					+ Messages.ERR_LAST_NAME_IS_EMPTY);
			result = false;
		}
		if (login != null && !login.isEmpty()) {
			Pattern pattern = Pattern
					.compile("^[a-zA-Z0-9\u0400-\u04FF_-]{3,16}$");
			Matcher matcher = pattern.matcher(login);
			if (!matcher.matches()) {
				errors.put(LOGIN, Messages.ERR_INVALID_LOGIN);
				LOG.debug(loginError
						+ Messages.ERR_INVALID_LOGIN);
				result = false;
			} else {
				if (DBManager.getInstance().findUserByLogin(login) != null) {
					LOG.debug("Find in db user by login --> " + login);
					errors.put(LOGIN,
							Messages.ERR_INVALID_LOGIN_USER_ALREADY_EXIST);
					LOG.debug(loginError
							+ Messages.ERR_INVALID_LOGIN_USER_ALREADY_EXIST);
					result = false;
				}
			}
		} else {
			errors.put(LOGIN, Messages.ERR_LOGIN_IS_EMPTY);
			LOG.debug(loginError
					+ Messages.ERR_LOGIN_IS_EMPTY);
			result = false;
		}
		if (email != null && !email.isEmpty()) {
			Pattern pattern = Pattern
					.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				errors.put(EMAIL, Messages.ERR_INVALID_EMAIL);
				LOG.debug(emailError
						+ Messages.ERR_INVALID_EMAIL);
				result = false;
			}
		} else {
			errors.put(EMAIL, Messages.ERR_EMAIL_IS_EMPTY);
			LOG.debug(emailError
					+ Messages.ERR_EMAIL_IS_EMPTY);
			result = false;
		}
		return result;
	}

}
