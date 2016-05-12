package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.Role;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

public class CreateCreaditCardCommand extends Command {

	private static final long serialVersionUID = -5825160100729521995L;
	private static final Logger LOG = Logger
			.getLogger(CreateCreaditCardCommand.class);
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

		// create map with info about users for jsp page select tag
		Map<Long, String> users = new LinkedHashMap<Long, String>();
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			//add for list only clients for whom we can  assigne credit card, not admins
			if(Role.getRole(user) == Role.CLIENT) {
			users.put(user.getId(), user.getFirstName() + " " + user.getLastName()
							+ "(" + user.getLogin() + ")");
			}
		}
		
		String currentLocale = (String) session.getAttribute("currentLocale");
		LOG.trace("Session attribute: currentLocale --> " + currentLocale);

		Locale locale = new Locale(currentLocale);
		String[] monthList = new DateFormatSymbols(locale).getShortMonths();
		Map<Integer, String> months = new LinkedHashMap<Integer, String>();
		for (int i = 0; i < monthList.length; i++) {
			months.put(i + 1, monthList[i]);
		}
		
		LOG.trace("Map of users --> " + users);
		// set attribute map of credit cards info to session
		session.setAttribute("users", users);
		LOG.trace("Session attribute: creditCards --> " + users);

		session.setAttribute("months", months);
		LOG.trace("Session attribute: creditCards --> " + users);
		
		String forward = Path.PAGE_CREATE_CREDIT_CARD;
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Command finished");
		return forward;
	}

}
