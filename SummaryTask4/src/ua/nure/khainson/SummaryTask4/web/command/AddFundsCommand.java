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
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Add funds to account by user command.
 * 
 * @author P.Khainson
 * 
 */
public class AddFundsCommand extends Command {

	private static final long serialVersionUID = 9216314133428360918L;
	private static final Logger LOG = Logger.getLogger(AddFundsCommand.class);
	private Map<String, String> errors = null;
	private static final String SUM_OF_MONEY = "sumOfMoney";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String successfulAddFunds = null;
		errors = new HashMap<String, String>();
		Long accountId = null;
		String sumOfMoney = null;
		// obtain number of account to add funds
		String id = request.getParameter("accountId");
		LOG.trace("Request parameter: accountId --> " + accountId);

		String forward = Path.PAGE_ERROR_PAGE;

		accountId = Long.parseLong(id);
		DBManager manager = DBManager.getInstance();

		sumOfMoney = request.getParameter(SUM_OF_MONEY + accountId);
		LOG.trace("Request attribute: sumOfMoney --> " + sumOfMoney);
		// obtain account from DB
		Account account = manager.findAccount(accountId);
		LOG.trace("Find from DB: account --> " + account);

		request.setAttribute("accountId", accountId);
		LOG.trace("set request atribute: errors --> " + errors);

		// validate entered sum of money
		if (validateSumOfMoney(sumOfMoney)) {
			LOG.debug("Sum on account > sum of payment.");
			// calc result on account after adding sum of money
			BigDecimal result = account.getSumOnAccount().add(
					BigDecimal.valueOf(Double.parseDouble(sumOfMoney)));
			LOG.trace("Result sum on account after adding funds: result --> "
					+ result);
			account.setSumOnAccount(result);
			// update account with new sum of money in DB
			manager.updateAccount(account);
			LOG.trace("Update in DB: account --> " + account);

			successfulAddFunds = "Succesful adding funds";
		} else {
			request.setAttribute("errors", errors);
			LOG.trace("Set request atribute: errors --> " + errors);
			request.setAttribute("successfulAddFunds", null);

			request.setAttribute(SUM_OF_MONEY, sumOfMoney);
			LOG.trace("Set request atribute: sumOfMoney --> " + sumOfMoney);
		}

		// execute command and get forward address
		Command command = CommandContainer.get("listUserAccounts");
		LOG.trace("Obtained command --> " + command);
		try {
			forward = command.execute(request, response);
			LOG.trace("Forward address --> " + forward);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			LOG.trace("Forward address --> " + forward);
		}
		request.setAttribute("successfulAddFunds", successfulAddFunds);
		LOG.trace("Set request atribute: successfulAddFunds --> "
				+ successfulAddFunds);
		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validation of sum of money to add to account
	 * 
	 * @param String
	 *            sum of money
	 * 
	 * @return boolean true if sum of money is valid, otherwise - false
	 */
	private boolean validateSumOfMoney(String sumOfMoney) {
		boolean validResult = true;
		final String sumOfMoneyError = SUM_OF_MONEY;
		if (sumOfMoney == null || sumOfMoney.isEmpty()) {
			errors.put(sumOfMoneyError,
					Messages.ERR_SUM_OF_MONEY_FIELD_IS_EMPTY);
			LOG.debug("Messages.ERR_SUM_OF_MONEY_FIELD_IS_EMPTY");
			validResult = false;
		} else {
			Pattern pattern = Pattern.compile("^\\d+(?:\\.?\\d{0,2})$");
			Matcher matcher = pattern.matcher(sumOfMoney);
			if (!matcher.matches()) {
				errors.put(sumOfMoneyError, Messages.ERR_INVALID_SUM_OF_MONEY);
				LOG.debug("Messages.ERR_INVALID_SUM_OF_MONEY");
				validResult = false;
			} else {
				if (Double.parseDouble(sumOfMoney) == 0) {
					errors.put(sumOfMoneyError, Messages.ERR_ZERO_SUM_OF_MONEY);
					LOG.debug("Messages.ERR_ZERO_SUM_OF_MONEY");
					validResult = false;
				}
				BigDecimal maxAvailableValOfSumOfMoney = new BigDecimal(
						"1000000000000.");
				if (!sumOfMoney.isEmpty()
						&& BigDecimal.valueOf(Double.parseDouble(sumOfMoney))
								.compareTo(maxAvailableValOfSumOfMoney) > 0) {
					errors.put(sumOfMoneyError,
							Messages.ERR_TOO_LARGE_SUM_OF_MONEY);
					LOG.debug("Messages.ERR_TOO_LARGE_SUM_OF_MONEY");
					validResult = false;
				}
			}

		}
		return validResult;
	}

}
