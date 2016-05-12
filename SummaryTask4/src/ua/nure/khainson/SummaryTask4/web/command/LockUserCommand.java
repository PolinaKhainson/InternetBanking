package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.LockStatus;
import ua.nure.khainson.SummaryTask4.db.entity.User;

import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Lock user command.
 * 
 * @author P.Khainson
 * 
 */
public class LockUserCommand extends Command {

	private static final long serialVersionUID = -5557414032247059677L;
	private static final Logger LOG = Logger.getLogger(LockUserCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String id = request.getParameter("userId");
		Long userId = null;
		if (id != null) {
			userId = Long.parseLong(id);
			// obtain user from DB
			DBManager manager = DBManager.getInstance();
			User user = manager.findUser(userId);
			LOG.trace("Find in DB: user --> " + user);
			// change user status for locked
			user.setLockStatusId(LockStatus.LOCKED.ordinal());
			// update user in DB
			manager.updateUser(user);
			LOG.trace("Update in DB: user --> " + user);
			// set user to request attribute
			request.setAttribute("user", user);
			LOG.trace("Set the request attribute: user --> " + user);
		} else {
			request.setAttribute("error",
					Messages.ERR_NO_CHECKED_ACCOUNTS_FOR_LOCKING);
			LOG.debug("Error: Messages.ERR_NO_CHECKED_ACCOUNTS_FOR_LOCKING");
		}
		// obtain command and execute forward address
		Command command = CommandContainer.get("listUsers");
		LOG.trace("Obtained command --> " + command);
		String forward = Path.PAGE_ERROR_PAGE;
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
