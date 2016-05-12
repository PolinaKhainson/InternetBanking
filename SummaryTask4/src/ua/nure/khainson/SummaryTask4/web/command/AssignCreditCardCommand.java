package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

public class AssignCreditCardCommand extends Command {

	private static final long serialVersionUID = 8240184986019099359L;
	private static final Logger LOG = Logger
			.getLogger(AssignCreditCardCommand.class);
	

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String successfulAssignCreditCard = null;
		Map<String, String> errors = new HashMap<String, String>();

		String assignedCreditCardId = request
				.getParameter("assignedCreditCardId");
		LOG.trace("Request parameter: assignedCreditCardId --> "
				+ assignedCreditCardId);
		Long cardId = Long.parseLong(assignedCreditCardId);
		// obtain number of account to assign credit card
		String id = request.getParameter("accountId");
		LOG.trace("Request parameter: accountId --> " + id);
		Long accountId = Long.parseLong(id);
		String forward = Path.PAGE_ERROR_PAGE;
		if (cardId > 0) {
			DBManager.getInstance().insertAccountIdCreditCardId(accountId,
					cardId);
			successfulAssignCreditCard = "Successful asignment!";
			// execute command and get forward address
		} else {
			errors.put("assignedCreditCardId",
					Messages.ERR_ASSIGNED_CAREDIT_CARD_ID_FIELD_IS_EMPTY);
		}
		request.setAttribute("successfulAssignCreditCard", successfulAssignCreditCard);
		LOG.trace("Set request atribute: successfulAssignCreditCard --> "
				+ successfulAssignCreditCard);
		request.setAttribute("accountId", id);
		LOG.trace("Set request atribute: acountId --> "
				+ id);
		request.setAttribute("errors", errors);
		LOG.trace("Set request atribute: errors --> "
				+ errors);
		Command command = CommandContainer.get("listAllAccounts");
		LOG.trace("Obtained command --> " + command);
		try {
			forward = command.execute(request, response);
			LOG.trace("Forward address --> " + forward);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			LOG.trace("Forward address --> " + forward);
		}
		
		LOG.debug("Command finished");

		return forward;
	}
}
