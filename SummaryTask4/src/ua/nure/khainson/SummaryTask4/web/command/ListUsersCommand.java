package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of users command.
 * 
 * @author P.Khainson
 * 
 */
public class ListUsersCommand extends Command {

	private static final long serialVersionUID = 620668493375121363L;
	private static final Logger LOG = Logger.getLogger(ListUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Set session attribute: session --> " + session);
		// get all users from DB
		List<User> userList = DBManager.getInstance().findUsers();
		LOG.trace("Found in DB: usersList --> " + userList);

		// sort users by user id
		Collections.sort(userList, new Comparator<User>() {
			public int compare(User o1, User o2) {
				return (int) (o1.getId() - o2.getId());
			}
		});

		// put users list to the request
		session.setAttribute("userList", userList);
		LOG.trace("Set the request attribute: userList --> " + userList);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_USERS;
	}

}
