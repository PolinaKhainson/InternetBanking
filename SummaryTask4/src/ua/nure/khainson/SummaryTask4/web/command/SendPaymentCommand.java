package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Send payment command.
 * 
 * @author P.Khainson
 * 
 */
public class SendPaymentCommand extends Command {
	private static final Logger LOG = Logger
			.getLogger(SendPaymentCommand.class);
	private static final long serialVersionUID = -8118302443627275666L;

	private static final String SUM_OF_MONEY = "sumOfMoney";
	private static final String FORWARD_ADDRESS = "Forward address --> ";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		// obtain selected cardId, accountId and sum of money from request
		String selectedCardId = request.getParameter("selectedCardId");
		LOG.trace("Request parameter: selectedCardId --> " + selectedCardId);
		String selectedAccountId = request.getParameter("selectedAccountId");
		LOG.trace("Request parameter: selectedAccountId --> "
				+ selectedAccountId);
		String selectedSumOfMoney = request.getParameter(SUM_OF_MONEY);
		LOG.trace("Request parameter: selectedSumOfMoney --> "
				+ selectedSumOfMoney);

		// execute command and get forward address
		String forward = Path.PAGE_ERROR_PAGE;
		if (Long.parseLong(selectedCardId) < 1) {
			// if card don't selected return to create payment page
			forward = Path.PAGE_CREATE_PAYMENT;
		}
		if (selectedCardId != null && Long.parseLong(selectedCardId) > 0) {
			// if card is selected set cardId and Sum of payment to request
			Long cardId = Long.parseLong(selectedCardId);
			request.setAttribute("creditCardId", cardId);
			LOG.trace("Set the request attribute: creditCardId --> " + cardId);
			if (selectedSumOfMoney != null) {
				request.setAttribute(SUM_OF_MONEY, selectedSumOfMoney);
				LOG.trace("Request attribute: sumOfMoney --> "
						+ selectedSumOfMoney);
			}
			// go forward to find all account for selected credit card
			Command command = CommandContainer.get("accountsForSelectedCardId");
			LOG.trace("Obtained command --> " + command);
			try {
				forward = command.execute(request, response);
			} catch (AppException ex) {
				request.setAttribute("errorMessage", ex.getMessage());
			}
			LOG.trace(FORWARD_ADDRESS + forward);

			LOG.debug("Send Payment comand finshed, now go to " + FORWARD_ADDRESS
					+ forward);
		}

		// cancel payment if cancel button was clicked on create_payment.jsp
		// page
		String cancelPayment = request.getParameter("cancelPayment");
		LOG.trace("Request parameter: cancelPayment --> " + cancelPayment);
		if (cancelPayment != null) {
			// forward to cancelPayent command
			Command command = CommandContainer.get("cancelPayment");
			LOG.trace("Obtained command --> " + command);
			try {
				forward = command.execute(request, response);
			} catch (AppException ex) {
				request.setAttribute("errorMessage", ex.getMessage());
			}
			LOG.trace(FORWARD_ADDRESS + forward);

			LOG.debug("Cancel Payment comand finshed, now go to " + FORWARD_ADDRESS
					+ forward);
		}

		// confirm or prepare payment if
		// if corresponding button was clicked on create_payment.jsp page
		String preparePayment = request.getParameter("preparePayment");
		LOG.trace("Request parameter: preparePayment --> " + preparePayment);
		String confirmPayment = request.getParameter("confirmPayment");
		LOG.trace("Request parameter: confirmPayment --> " + confirmPayment);
		if (preparePayment != null || confirmPayment != null) {
			Command command = null;
			if (preparePayment != null) {
				command = CommandContainer.get("preparePayment");
			}
			if (confirmPayment != null) {
				command = CommandContainer.get("confirmPayment");
			}
			LOG.trace("Obtained command --> " + command);
			// validate parameters of payment
			boolean validationSuccesful = validateFields(selectedCardId,
					selectedAccountId, selectedSumOfMoney, request);
			LOG.trace("Validation of fields --> " + validationSuccesful);
			if (validationSuccesful) {
				Long cardId = Long.parseLong(selectedCardId);
				Long accountId = Long.parseLong(selectedAccountId);
				BigDecimal sumOfMoney = BigDecimal.valueOf(Double
						.parseDouble(selectedSumOfMoney));
				// set payment parameters to request
				request.setAttribute("selectedCardId", cardId);
				LOG.trace("Set the request attribute:  selectedCardId --> "
						+ cardId);
				request.setAttribute("selectedAccountId", accountId);
				LOG.trace("Set the request attribute:  selectedAccountId --> "
						+ accountId);
				request.setAttribute(SUM_OF_MONEY, sumOfMoney);
				LOG.trace("Set the request attribute:  sumOfMoney --> "
						+ sumOfMoney);
				// execute command confirm or prepare and forward to address
				try {
					forward = command.execute(request, response);
				} catch (AppException ex) {
					request.setAttribute("errorMessage", ex.getMessage());
				}
				LOG.trace(FORWARD_ADDRESS + forward);

				LOG.debug("Send Payment comand finshed, now go to " + FORWARD_ADDRESS
						+ forward);
			} else {
				// forward back to create pament page if validation failed
				forward = Path.PAGE_CREATE_PAYMENT;
				LOG.trace(FORWARD_ADDRESS + forward);
			}
		}
		LOG.debug("Command finishes");
		return forward;
	}

	/**
	 * Validate fields: id of credit card, id of acount, sum of money.
	 * 
	 * @param cardId
	 *            Long id of credit card
	 * @param accountId
	 *            Long id of account
	 * @param sumOfMoney
	 *            String sum of money for payment
	 * @param request
	 *            HttpServletRequest request
	 * 
	 * @return boolean true if all params are valid, otherwise - false
	 * 
	 */
	private boolean validateFields(String cardId, String accountId,
			String sumOfMoney, HttpServletRequest request) {
		boolean validResult = true;
		Map<String, String> errors = new HashMap<String, String>();
		System.out.println(cardId + " " + accountId + " " + sumOfMoney);
		if (cardId == null || Long.parseLong(cardId) < 1) {
			errors.put("selectedCardId",
					Messages.ERR_CREDIT_CARD_MUST_BE_CHOSEN);
			validResult = false;
		}
		if (accountId == null || Long.parseLong(accountId) < 1) {
			errors.put("selectedAccountId", Messages.ERR_ACCOUNT_MUST_BE_SHOSEN);
			validResult = false;
		}
		if (sumOfMoney == null || sumOfMoney.isEmpty()) {
			errors.put(SUM_OF_MONEY, Messages.ERR_SUM_OF_MONEY_FIELD_IS_EMPTY);
			validResult = false;
		} else {
			Pattern pattern = Pattern.compile("^\\d+(?:\\.?\\d{0,2})$");
			Matcher matcher = pattern.matcher(sumOfMoney);
			if (!matcher.matches()) {
				errors.put(SUM_OF_MONEY, Messages.ERR_INVALID_SUM_OF_MONEY);
				validResult = false;
			} else {
				BigDecimal maxAvailableValOfSumOfMoney = new BigDecimal(
						"1000000000000.");
				if (!sumOfMoney.isEmpty()
						&& BigDecimal.valueOf(Double.parseDouble(sumOfMoney))
								.compareTo(maxAvailableValOfSumOfMoney) > 0) {
					errors.put(SUM_OF_MONEY,
							Messages.ERR_TOO_LARGE_SUM_OF_MONEY);
					validResult = false;
				}
				if (BigDecimal.valueOf(Double.parseDouble(sumOfMoney))
						.compareTo(BigDecimal.ZERO) == 0) {
					errors.put(SUM_OF_MONEY,
							Messages.ERR_SUM_OF_MONEY_CANNOT_EQUAL_ZERO);
					validResult = false;
				}

			}

		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		System.out.println(errors);
		return validResult;

	}

}
