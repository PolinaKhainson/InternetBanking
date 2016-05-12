package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Cancel payment command.
 * 
 * @author P.Khainson
 * 
 */

public class CancelPaymentCommand extends Command {

	private static final long serialVersionUID = 7047838253016502381L;
	private static final Logger LOG = Logger
			.getLogger(CancelPaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		// no op
		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_PAYMENTS;
	}

}
