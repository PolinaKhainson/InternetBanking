package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.DBException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

public class SendCreditCardCommand extends Command {

	private static final long serialVersionUID = -4381543663941743361L;
	
	private static final Logger LOG = Logger
			.getLogger(SendCreditCardCommand.class);
	
	private static final String FORWARD_ADDRESS = "Forward address --> ";
	
	private Map<String, String> errors;
	
	private static final String CREDIT_CARD_NUMBER = "creditCardNumber";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		// obtain selected userId, creditCardNumber, selected year and month
		// from request
		String selectedUserId = request.getParameter("selectedUserId");
		LOG.trace("Request parameter: selectedUserId --> " + selectedUserId);
		String creditCardNumber = request.getParameter(CREDIT_CARD_NUMBER);
		LOG.trace("Request parameter: creditCard --> " + creditCardNumber);
		String selectedYear = request.getParameter("selectedYear");
		LOG.trace("Request parameter: selectedYear --> " + selectedYear);
		String selectedMonth = request.getParameter("selectedMonth");
		LOG.trace("Request parameter: selectedMonth --> " + selectedMonth);
		// create forward address
		String forward = Path.PAGE_ERROR_PAGE;

		// if cancel button was clicked on create_account.jsp
		// page
		String cancel = request.getParameter("cancel");
		LOG.trace("Request parameter: cancelPayment --> " + cancel);
		if (cancel != null) {
			forward = Path.PAGE_LIST_ALL_ACCOUNTS;
			LOG.trace(FORWARD_ADDRESS + forward);
		}

		// create credit card if Create credit card button was clicked
		// on create_credit_card.jsp page
		String create = request.getParameter("create");
		LOG.trace("Request parameter: create --> " + create);
		if (create != null) {
			// validate parameters of payment
			boolean validationSuccesful = validateFields(selectedUserId,
					creditCardNumber, selectedYear, selectedMonth);
			LOG.trace("Validation of fields --> " + validationSuccesful);
			if (validationSuccesful) {
				Long userId = Long.parseLong(selectedUserId);
				int cardNumber = Integer.parseInt(creditCardNumber);
				// Create new credit card
				CreditCard creditCard = new CreditCard();
				creditCard.setUserId(userId);
				creditCard.setCardNumber(cardNumber);
				Date endingDate;
				try {
					java.util.Date dt = new SimpleDateFormat("yyyy-MM-dd")
							.parse(selectedYear + "-" + selectedMonth + "-01");
					endingDate = new Date(dt.getTime());
					creditCard.setEndingDate(endingDate);
				} catch (ParseException e) {
					LOG.error("Cannot parse: date --> " + selectedYear + "-"
							+ selectedMonth + "-01");
				}
				LOG.trace("Create creditCard:  creditCard --> " + creditCard);
				// Insert credit card in DB
				DBManager.getInstance().insertCreditCard(creditCard);
				LOG.trace("Insert in DB:  creditCard --> " + creditCard);

				Command command = CommandContainer.get("listAllAccounts");
				// execute command confirm or prepare and forward to address
				try {
					forward = command.execute(request, response);
				} catch (AppException ex) {
					request.setAttribute("errorMessage", ex.getMessage());
				}
				LOG.trace(FORWARD_ADDRESS + forward);
			} else {
				// forward back to create account page if validation failed
				request.setAttribute("errors", errors);
				LOG.debug("Errors of validation: errors --> " + errors);

				// set account parameters to request
				request.setAttribute("selectedUserId", selectedUserId);
				LOG.trace("Set the request attribute:  selectedUserId --> "
						+ selectedUserId);
				request.setAttribute(CREDIT_CARD_NUMBER, creditCardNumber);
				LOG.trace("Set the request attribute:  creditCardNumber --> "
						+ creditCardNumber);
				request.setAttribute("selectedYear", selectedYear);
				LOG.trace("Set the request attribute:  selectedYear --> "
						+ selectedYear);
				request.setAttribute("selectedMonth", selectedMonth);
				LOG.trace("Set the request attribute:  selectedMonth --> "
						+ selectedMonth);

				forward = Path.PAGE_CREATE_CREDIT_CARD;
				LOG.trace(FORWARD_ADDRESS + forward);
			}
		}
		LOG.debug("Command finishes");
		return forward;
	}

	/**
	 * Validate fields: user id, number of credit card, selected year and month
	 * 
	 * @param selectedUserId
	 *            String id of user for credit card
	 * @param creditCardNumber
	 *            String credit card number
	 * @param selectedYear
	 *            String selected year
	 * @param selectedMonth
	 *            String selected month
	 * @param request
	 *            HttpServletRequest request
	 * 
	 * @return boolean true if all params are valid, otherwise - false
	 * @throws DBException
	 * @throws NumberFormatException
	 * 
	 */
	private boolean validateFields(String selectedUserId,
			String creditCardNumber, String selectedYear, String selectedMonth)
			throws DBException {
		boolean validResult = true;
		errors = new HashMap<String, String>();
		if (selectedUserId == null || Long.parseLong(selectedUserId) < 1) {
			errors.put("selectedUserId", Messages.ERR_USER_MUST_BE_CHOSEN);
			validResult = false;
		}
		if (creditCardNumber == null || creditCardNumber.isEmpty()) {
			errors.put("accountNumber",
					Messages.ERR_CREDIT_CARD_NUMBER_FIELD_IS_EMPTY);
			validResult = false;
		}
		Pattern patternCcNum = Pattern.compile("[0-9]{8}");

		if (creditCardNumber != null
				&& !patternCcNum.matcher(creditCardNumber).matches()) {
			errors.put(CREDIT_CARD_NUMBER,
					Messages.ERR_INVALID_CREDIT_CARD_NUMBER);
			validResult = false;
		}
		if (creditCardNumber != null
				&& patternCcNum.matcher(creditCardNumber).matches()) {
			CreditCard creditCard = DBManager.getInstance()
					.findCreditCardByNumber(Integer.parseInt(creditCardNumber));
			if (creditCard != null) {
				errors.put(CREDIT_CARD_NUMBER,
						Messages.CREDIT_CARD_ALREADY_EXISTS);
				validResult = false;
			}
		}
		if (selectedYear == null || Long.parseLong(selectedYear) < 1) {
			errors.put("selectedYear", Messages.ERR_YEAR_MUST_BE_CHOSEN);
			validResult = false;
		}
		if (selectedMonth == null || Long.parseLong(selectedMonth) < 1) {
			errors.put("selectedMonth", Messages.ERR_MONTH_MUST_BE_CHOSEN);
			validResult = false;
		}
		System.out.println(errors);
		return validResult;

	}

}
