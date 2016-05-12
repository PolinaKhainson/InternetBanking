package ua.nure.khainson.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;

/**
 * Logout command.
 * 
 * @author P.Khainson
 * 
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = -1229797796220787772L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug("Command starts");
		HttpSession session = request.getSession(false);
		LOG.debug("Request parametr: session --> " + session);
		if (session != null) {
			// close session
			session.invalidate();
			LOG.debug("Session invalidate: session --> " + session);
		}

		LOG.debug("Command finished");
		return Path.PAGE_LOGIN;
	}

}