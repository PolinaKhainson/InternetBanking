package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.LockStatus;
import ua.nure.khainson.SummaryTask4.db.Role;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Login command.
 * 
 * @author P.Khainson
 * 
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -2671352784258394493L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		HttpSession session = request.getSession();
		LOG.trace("Request parameter: session --> " + session);

		// obtain login and password from a request
		DBManager manager = DBManager.getInstance();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: loging --> " + login);

		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);
		if (login == null || password == null || login.isEmpty()
				|| password.isEmpty()) {
			throw new AppException("Login/password cannot be empty");
		}
		// obtain user from DB by login
		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			throw new AppException("Cannot find user with such login/password");
		}

		if (LockStatus.getUserLockStatus(user).getName().equals("locked")) {
			throw new AppException(
					"User with such login/password is locked by admin");
		}
		// get user Role
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);
		// get forward address depend on user Role
		if (userRole == Role.ADMIN) {
			forward = Path.COMMAND_LIST_USERS;
			LOG.trace("Forward address --> " + forward);
		}

		if (userRole == Role.CLIENT) {
			forward = Path.COMMAND_LIST_USER_PAYMENTS;
			LOG.trace("Forward address --> " + forward);
		}
		// set user to request attribute
		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);
		// set user role to request attribute
		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as "
				+ userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}