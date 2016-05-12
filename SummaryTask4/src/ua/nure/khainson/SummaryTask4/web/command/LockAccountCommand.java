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
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Lock account command.
 * 
 * @author P.Khainson
 * 
 */
public class LockAccountCommand extends Command {
	private static final long serialVersionUID = 7775113210192353617L;
	private static final Logger LOG = Logger
			.getLogger(LockAccountCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String id = request.getParameter("accountId");
		LOG.trace("Request parameter: accountId --> " + id);
		Long accountId = null;
		if (id != null) {
			accountId = Long.parseLong(id);
			DBManager manager = DBManager.getInstance();
			// obtain account from DB by account id
			Account account = manager.findAccount(accountId);
			LOG.trace("Find in DB: account --> " + account);
			// change account status to lock
			account.setLockStatusId(LockStatus.LOCKED.ordinal());
			// update account in DB
			manager.updateAccount(account);
			LOG.trace("Update in DB: account --> " + account);
			// set account to request attribute
			request.setAttribute("account ", account);
			LOG.trace("Set the request attribute: account --> " + account);
		} else {
			request.setAttribute("error",
					Messages.ERR_NO_CHECKED_ACCOUNTS_FOR_LOCKING);
			LOG.trace("Error: Messages.ERR_NO_CHECKED_ACCOUNTS_FOR_LOCKING");
		}
		// Obtain FORWARD page depends on by whom lock Account command called
		HttpSession session = request.getSession();
		// obtain user from session attribute
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);
		// get user role from user
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		String forward = Path.PAGE_ERROR_PAGE;
		Command command = null;
		if (userRole == Role.ADMIN) {
			// forward page for admin is list of ALL accounts
			command = CommandContainer.get("listAllAccounts");
			LOG.trace("Obtained command --> " + command);
		}
		if (userRole == Role.CLIENT) {
			// forward page for client is list of accounts FOR THIS USER
			command = CommandContainer.get("listUserAccounts");
			LOG.trace("Obtained command --> " + command);
		}
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
