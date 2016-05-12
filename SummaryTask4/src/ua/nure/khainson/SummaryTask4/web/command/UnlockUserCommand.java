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

/**
 * Unlock User command.
 * 
 * @author P.Khainson
 * 
 */
public class UnlockUserCommand extends Command {

	private static final long serialVersionUID = 7828407362587011583L;
	private static final Logger LOG = Logger.getLogger(UnlockUserCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String id = request.getParameter("userId");
		LOG.trace("Request parameter: userId --> " + id);
		Long userId = null;
		userId = Long.parseLong(id);
		// obtain user from DB
		DBManager manager = DBManager.getInstance();
		User user = manager.findUser(userId);
		LOG.trace("Find in DB: user --> " + user);
		// change user status to unlocked
		user.setLockStatusId(LockStatus.UNLOCKED.ordinal());
		// update user in DB
		manager.updateUser(user);
		LOG.trace("Update in DB: user --> " + user);

		request.setAttribute("user ", user);
		LOG.trace("Set the request attribute: user --> " + user);
		// execute command and forward to address
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
