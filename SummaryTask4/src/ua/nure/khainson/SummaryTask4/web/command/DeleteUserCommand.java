package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

public class DeleteUserCommand extends Command {

	private static final long serialVersionUID = -1232019074915630587L;

	private static final Logger LOG = Logger.getLogger(DeleteUserCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String id = request.getParameter("userId");
		LOG.trace("Request parameter: userId --> " + id);
		Long userId = Long.parseLong(id);
		DBManager manager = DBManager.getInstance();
		// obtain user from DB
		User user = manager.findUser(userId);
		LOG.trace("Find in DB: user --> " + user);
		if (user != null) {
			// delete user from DB if exists
			manager.deleteUser(userId);
			LOG.trace("Delete from DB: user --> " + user);
		}
		// obtain command and forward to executed address
		Command command = CommandContainer.get("listUsers");
		LOG.trace("Obtained command --> " + command);
		String forward = Path.PAGE_ERROR_PAGE;
		try {
			forward = command.execute(request, response);
			LOG.trace("Forward address --> " + forward);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			LOG.error("Error message --> " + ex.getMessage());
		}
		LOG.debug("Command finished");
		return forward;
	}

}
