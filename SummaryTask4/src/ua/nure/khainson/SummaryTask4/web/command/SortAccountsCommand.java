package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.bean.AccountCreditCardsBean;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Sort accounts command.
 * 
 * @author P.Khainson
 * 
 */
public class SortAccountsCommand extends Command {

	private static final long serialVersionUID = -5205293064938896323L;
	private static final Logger LOG = Logger
			.getLogger(SortAccountsCommand.class);

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Request parameter: session --> " + session);

		// obtain accounts with their credit cards from session
		List<AccountCreditCardsBean> accountCreditCardsBeanList = (List<AccountCreditCardsBean>) session
				.getAttribute("accountCreditCardsBeanList");
		LOG.trace("Session parameter: accountCreditCardsBeanList --> "
				+ accountCreditCardsBeanList);
		
		//get witch column must be sorted
		String columnName = request.getParameter("columnName");
		LOG.trace("Get from request: columnName --> " + columnName);
		
		//orderAccountNumber != null if order number column of account table has been chosen 
		String orderAccountNumber = request.getParameter("orderAccountNumber");
		LOG.trace("Get from request: orderAccountNumber --> "
				+ orderAccountNumber);
		
		//orderInitAccountNumber != null if INIT order number
		//(id of account from DB)column of account table has been chosen 
		String orderInitAccountNumber = request
				.getParameter("orderInitAccountNumber");
		LOG.trace("Get from request: orderInitAccountNumber --> "
				+ orderInitAccountNumber);
		
		//orderAccountSumOfMoney != null if sum of money column of account table has been chosen
		String orderAccountSumOfMoney = request
				.getParameter("orderAccountSumOfMoney");
		LOG.trace("Get from request: orderAccountSumOfMoney --> "
				+ orderAccountSumOfMoney);
		
		//columnName = orderAccountNumber - just reverse order in list of accounts
		if (columnName.equals("orderAccountNumber")) {
			List<AccountCreditCardsBean> list = new ArrayList<AccountCreditCardsBean>();
			for (int i = accountCreditCardsBeanList.size() - 1; i >= 0; i--) {
				list.add(accountCreditCardsBeanList.get(i));
			}
			accountCreditCardsBeanList = list;
		}
		//columnName = initAccountNumber and order = desc - sort accounts list desc by account id
		if (columnName.equals("initAccountNumber")
				&& orderInitAccountNumber.equals("desc")) {
			Collections.sort(accountCreditCardsBeanList,
					new Comparator<AccountCreditCardsBean>() {
						public int compare(AccountCreditCardsBean o1,
								AccountCreditCardsBean o2) {
							return -(int) (o1.getAccount().getAccountNumber() - o2
									.getAccount().getAccountNumber());
						}
					});
		}
		//columnName = initAccountNumber and order = asc - sort accounts list asc by account id
		if (columnName.equals("initAccountNumber")
				&& orderInitAccountNumber.equals("asc")) {
			Collections.sort(accountCreditCardsBeanList,
					new Comparator<AccountCreditCardsBean>() {
						public int compare(AccountCreditCardsBean o1,
								AccountCreditCardsBean o2) {
							return (int) (o1.getAccount().getAccountNumber() - o2
									.getAccount().getAccountNumber());
						}
					});
		}
		//columnName = accountSumOfMoney and order = desk - sort accounts list desk by sum of money
		if (columnName.equals("accountSumOfMoney")
				&& orderAccountSumOfMoney.equals("desc")) {
			Collections.sort(accountCreditCardsBeanList,
					new Comparator<AccountCreditCardsBean>() {
						public int compare(AccountCreditCardsBean o1,
								AccountCreditCardsBean o2) {
							return (o2.getAccount().getSumOnAccount()
									.compareTo(o1.getAccount()
											.getSumOnAccount()));
						}
					});
		}
		//columnName = accountSumOfMoney and order = asc - sort accounts list ask by sum of money
		if (columnName.equals("accountSumOfMoney")
				&& orderAccountSumOfMoney.equals("asc")) {
			Collections.sort(accountCreditCardsBeanList,
					new Comparator<AccountCreditCardsBean>() {
						public int compare(AccountCreditCardsBean o1,
								AccountCreditCardsBean o2) {
							return (o1.getAccount().getSumOnAccount()
									.compareTo(o2.getAccount()
											.getSumOnAccount()));
						}

					});
		}

		//set accounts sorted list to session attribute
		session.setAttribute("accountCreditCardsBeanList",
				accountCreditCardsBeanList);
		LOG.trace("Set the session attribute: accountCreditCardsBeanList --> "
				+ accountCreditCardsBeanList);
		// set current order of column AccountNumber to session attribute
		session.setAttribute("orderAccountNumber", orderAccountNumber);
		LOG.trace("Set the session attribute: orderAccountNumber --> "
				+ orderAccountNumber);
		// set current order of column InitAccountNumber to session attribute
		session.setAttribute("orderInitAccountNumber", orderInitAccountNumber);
		LOG.trace("Set the session attribute: orderInitAccountNumber --> "
				+ orderInitAccountNumber);
		// set current order of column orderAccountSumOfMoney to session attribute
		session.setAttribute("orderAccountSumOfMoney", orderAccountSumOfMoney);
		LOG.trace("Set the session attribute: orderAccountSumOfMoney --> "
				+ orderAccountSumOfMoney);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_ACCOUNTS;
	}
}
