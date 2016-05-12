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
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.LockStatus;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.DBException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

public class SendAccountCommand extends Command {

	private static final long serialVersionUID = 6881644938494228755L;
	private static final Logger LOG = Logger
			.getLogger(SendAccountCommand.class);
	private Map<String, String> errors;

	private static final String FORWARD_ADDRESS = "Forward address --> ";
	
	private static final String ACCOUNT_NUMBER = "accountNumber";
	
	private static final String SUM_ON_ACCOUNT = "sumOnAccount";
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		// obtain selected userId, accountNumber and sum on account from request
		String selectedUserId = request.getParameter("selectedUserId");
		LOG.trace("Request parameter: selectedUserId --> " + selectedUserId);
		String accountNumber = request.getParameter(ACCOUNT_NUMBER);
		LOG.trace("Request parameter: accountNumber --> " + accountNumber);
		String sumOnAccount = request.getParameter(SUM_ON_ACCOUNT);
		LOG.trace("Request parameter: sumOnAccount --> " + sumOnAccount);
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

		// create account if Create account button was clicked
		// on create_account.jsp page
		String create = request.getParameter("create");
		LOG.trace("Request parameter: create --> " + create);
		if (create != null) {
			// validate parameters of payment
			boolean validationSuccesful = validateFields(selectedUserId,
					accountNumber, sumOnAccount);
			LOG.trace("Validation of fields --> " + validationSuccesful);
			if (validationSuccesful) {
				Long userId = Long.parseLong(selectedUserId);
				int accountNum = Integer.parseInt(accountNumber);
				BigDecimal sumOnAcc = new BigDecimal(sumOnAccount);

				// Create new account
				Account account = new Account();
				account.setLockStatusId(LockStatus.UNLOCKED.ordinal());
				account.setUserId(userId);
				account.setAccountNumber(accountNum);
				account.setSumOnAccount(sumOnAcc);
				LOG.trace("Create account:  account --> " + account);
				// Insert account in DB
				DBManager.getInstance().insertAccount(account);
				LOG.trace("Insert in DB:  account --> " + account);

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
				request.setAttribute(ACCOUNT_NUMBER, accountNumber);
				LOG.trace("Set the request attribute:  accountNumber --> "
						+ accountNumber);
				request.setAttribute(SUM_ON_ACCOUNT, sumOnAccount);
				LOG.trace("Set the request attribute:  sumOnAccount --> "
						+ sumOnAccount);

				forward = Path.PAGE_CREATE_ACCOUNT;
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
	 * @throws DBException 
	 * @throws NumberFormatException 
	 * 
	 */
	private boolean validateFields(String selectedUserId, String accountNumber,
			String sumOnAccount) throws DBException {
		boolean validResult = true;
		errors = new HashMap<String, String>();
		if (selectedUserId == null || Long.parseLong(selectedUserId) < 1) {
			errors.put("selectedUserId", Messages.ERR_USER_MUST_BE_CHOSEN);
			validResult = false;
		}
		if (accountNumber == null || accountNumber.isEmpty()) {
			errors.put(ACCOUNT_NUMBER,
					Messages.ERR_ACCOUNT_NUMBER_FIELD_IS_EMPTY);
			validResult = false;
		}
		Pattern patternAccNum = Pattern.compile("[0-9]{6}");

		if (accountNumber != null
				&& !patternAccNum.matcher(accountNumber).matches()) {
			errors.put(ACCOUNT_NUMBER, Messages.ERR_INVALID_ACCOUNT_NUMBER);
			validResult = false;
		}
		if (accountNumber != null
				&& patternAccNum.matcher(accountNumber).matches()) {
			Account account = DBManager.getInstance().findAccountByNumber(
					Integer.parseInt(accountNumber));
			if (account != null) {
				errors.put(ACCOUNT_NUMBER, Messages.ACCOUNT_ALREADY_EXISTS);
				validResult = false;
			}
		}
		if (sumOnAccount == null || sumOnAccount.isEmpty()) {
			errors.put(SUM_ON_ACCOUNT, Messages.ERR_SUM_OF_MONEY_FIELD_IS_EMPTY);
			validResult = false;
		} else {
			Pattern pattern = Pattern.compile("^\\d+(?:\\.?\\d{0,2})$");
			Matcher matcher = pattern.matcher(sumOnAccount);
			if (!matcher.matches()) {
				errors.put(SUM_ON_ACCOUNT, Messages.ERR_INVALID_SUM_OF_MONEY);
				validResult = false;
			} else {
				BigDecimal maxAvailableValOfSumOfMoney = new BigDecimal(
						"1000000000000.");
				if (!SUM_ON_ACCOUNT.isEmpty()
						&& new BigDecimal(sumOnAccount)
								.compareTo(maxAvailableValOfSumOfMoney) > 0) {
					errors.put(SUM_ON_ACCOUNT,
							Messages.ERR_TOO_LARGE_SUM_OF_MONEY);
					validResult = false;
				}
			}
		}

		System.out.println(errors);
		return validResult;

	}

}
