package ua.nure.khainson.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.web.command.Command;
import ua.nure.khainson.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet controller.
 * 
 * @author P.Khainson
 * 
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = -5653304709130020596L;
	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);
		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);
		// execute command and get forward address
		String forward = Path.PAGE_ERROR_PAGE;

		try {
			forward = command.execute(request, response);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}

		// go to forward
		if (forward != null) {
			LOG.trace("Forward address --> " + forward);

			LOG.debug("Controller finished, now go to forward address --> "
					+ forward);
			request.getRequestDispatcher(forward).forward(request, response);
		}

		LOG.debug("Controller finished");
	}

}