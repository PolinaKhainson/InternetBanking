package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.LockStatus;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of accounts for selected credit card command.
 * 
 * @author P.Khainson
 * 
 */
public class ListAccountsForSelectedCreditCardCommand extends Command {

	private static final long serialVersionUID = 960891899014617029L;
	private static final Logger LOG = Logger
			.getLogger(ListAccountsForSelectedCreditCardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Request parameter: session --> " + session);

		// obtain credit card id from request
		Long creditCardId = (Long) request.getAttribute("creditCardId");
		LOG.trace("Request parameter: creditCardId --> " + creditCardId);

		// get accounts list from DB
		List<Account> accountList = DBManager.getInstance().findAccounts(
				creditCardId);
		LOG.trace("Found in DB: accounts --> " + accountList);

		// sort accounts by account number
		Collections.sort(accountList, new Comparator<Account>() {
			@Override
			public int compare(Account o1, Account o2) {
				return (int) (o1.getAccountNumber() - o2.getAccountNumber());
			}
		});
		// put accounts info to map for representing on jsp page
		Map<Long, String> accounts = new LinkedHashMap<Long, String>();
		for (int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			accounts.put(account.getId(), account.getAccountNumber()
					+ ", balance: " + account.getSumOnAccount());
		}
		// put account statuses info to map for representing on jsp page
		Map<Long, String> accountStatuses = new LinkedHashMap<Long, String>();
		for (int i = 0; i < accountList.size(); i++) {
			Account account = accountList.get(i);
			accountStatuses.put(account.getId(), LockStatus
					.getAccountLockStatus(account).getName());
		}
		session.setAttribute("accounts", accounts);
		LOG.trace("Set the session attribute: accounts --> " + accounts);
		session.setAttribute("accountStatuses", accountStatuses);
		LOG.trace("Set the session attribute: accountStatuses --> "
				+ accountStatuses);
		request.setAttribute("creditCardId", creditCardId);
		LOG.trace("Set the session attribute: creditCardId --> " + creditCardId);

		String sumOfMoney = (String) request.getAttribute("sumOfMoney");
		LOG.trace("Get request attribute: sumOfMoney --> " + sumOfMoney);

		if (sumOfMoney != null) {
			// set request attribute sum of money for representing on jsp
			// if there was some invalid data input on create Payment page
			request.setAttribute("sumOfMoney", sumOfMoney);
			LOG.trace("Set request attribute: sumOfMoney --> " + sumOfMoney);
		}
		String selectedAccountId = request.getParameter("selectedAccountId");
		LOG.trace("Request parameter: selectedAccountId --> "
				+ selectedAccountId);
		if (selectedAccountId != null && Long.parseLong(selectedAccountId) > 0) {
			// if some account has already been chosen on create page
			DBManager manager = DBManager.getInstance();
			Long accountId = Long.parseLong(selectedAccountId);
			Account account = manager.findAccount(accountId);
			LOG.trace("Found in DB: account --> " + account);
			BigDecimal sumOnAccount = account.getSumOnAccount();
			request.setAttribute("sumOnAccount", sumOnAccount);
			LOG.trace("Set request attribute: sumOnAccount --> " + sumOnAccount);
			// set request attribute account id
			request.setAttribute("selectedAccountId", accountId);
			LOG.trace("Set request attribute: selectedAccountId --> "
					+ accountId);
		}

		LOG.debug("Command finished");
		return Path.PAGE_CREATE_PAYMENT;
	}
}
