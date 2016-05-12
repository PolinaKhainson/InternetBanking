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
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.User;

import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Unlock account command.
 * 
 * @author P.Khainson
 * 
 */
public class UnlockAccountCommand extends Command {
	private static final long serialVersionUID = 7775113210192353617L;
	private static final Logger LOG = Logger
			.getLogger(UnlockAccountCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		LOG.trace("Get session from request: session --> " + session);

		// obtain user from session attribute
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);
		// get user role
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		String id = request.getParameter("accountId");
		LOG.trace("Request parameter: accountId --> " + id);
		Long accountId = Long.parseLong(id);
		// obtain account for unlocking from DB by id
		DBManager manager = DBManager.getInstance();
		Account account = manager.findAccount(accountId);
		LOG.trace("Find in DB: account --> " + account);
		// set new status after unlocking depend on user role
		if (userRole == Role.ADMIN) {
			// with admin role after unlocking - unlocked account
			account.setLockStatusId(LockStatus.UNLOCKED.ordinal());
		}
		if (userRole == Role.CLIENT) {
			// with client after unlocking - waiting for unlock account
			account.setLockStatusId(LockStatus.WAITING_FOR_UNLOCK.ordinal());
		}
		// update account in db
		manager.updateAccount(account);
		LOG.trace("Update in DB: account --> " + account);

		request.setAttribute("account ", account);
		LOG.trace("Set the request attribute: account --> " + account);
		// execute command and forward to address
		String forward = Path.PAGE_ERROR_PAGE;
		Command command = null;
		if (userRole == Role.ADMIN) {
			command = CommandContainer.get("listAllAccounts");
		}
		if (userRole == Role.CLIENT) {
			command = CommandContainer.get("listUserAccounts");
		}
		LOG.trace("Obtained command --> " + command);
		try {
			forward = command.execute(request, response);
			LOG.trace("Forward address --> " + forward);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}

		LOG.debug("Command finished");
		return forward;
	}
}
