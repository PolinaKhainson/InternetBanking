package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.Role;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Change locale command. Changes the language of application interface.
 * 
 * @author P.Khainson
 * 
 */
public class ChangeLocaleCommand extends Command {

	private static final long serialVersionUID = -4530604260768610330L;

	private static final Logger LOG = Logger
			.getLogger(ChangeLocaleCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		// obtain curret local from request
		String currentLocale = request.getParameter("currentLocale");
		LOG.trace("Request parameter: currentLocale --> " + currentLocale);

		Locale locale = new Locale(currentLocale);
		HttpSession session = request.getSession();
		// set locale to config
		Config.set(session, Config.FMT_LOCALE, locale);
		// set resources file (bundle) for current locale
		ResourceBundle bundle = ResourceBundle.getBundle("resources", locale);

		session.setAttribute("currentLocale", locale);
		LOG.trace("Session attribute: currentLocale --> " + locale);

		session.setAttribute("resources", bundle);
		LOG.trace("Session attribute: resources --> " + bundle);

		// obtain the forward page address depend on from where language have
		// been changed
		String commonPage = request.getParameter("commonPage");
		LOG.trace("Request parameter: commonPage --> " + commonPage);

		String pageName = request.getParameter("pageName");
		LOG.trace("Request parameter: pageName --> " + pageName);

		String loginPage = request.getParameter("loginPage");
		LOG.trace("Request parameter: loginPage --> " + loginPage);

		String registrationPage = request.getParameter("registrationPage");
		LOG.trace("Request parameter: loginPage --> " + registrationPage);

		String firstName = request.getParameter("firstNAme");
		LOG.trace("Request parameter: firstName --> " + firstName);
		String lastName = request.getParameter("lastName");
		LOG.trace("Request parameter: lastName --> " + lastName);

		String forward = Path.PAGE_ERROR_PAGE;
		// if request for changing language
		// came from login.jsp page or registration.jsp page
		if (loginPage != null || registrationPage != null) {
			return pageName;
		}

		if (commonPage == null || commonPage.isEmpty()) {
			User user = (User) session.getAttribute("user");
			LOG.trace("Session attribute: user --> " + user);
			Role userRole = Role.getRole(user);
			LOG.trace("userRole --> " + userRole);
			// if request for changing language
			// came from admin access page
			if (userRole == Role.ADMIN) {
				forward = Path.ADMIN_PAGES_PATH + pageName;
				LOG.trace("Forward address --> " + forward);
			}
			// if request for changing language
			// came from client access page
			if (userRole == Role.CLIENT) {
				forward = Path.CLIENT_PAGES_PATH + pageName;
				LOG.trace("Forward address --> " + forward);
			}
		} else {
			// if request for changing language
			// came from common access page
			forward = Path.COMMON_PAGES_PATH + pageName;
			LOG.trace("Forward address --> " + forward);
		}

		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		LOG.debug("Command finished");
		return forward;
	}

}
