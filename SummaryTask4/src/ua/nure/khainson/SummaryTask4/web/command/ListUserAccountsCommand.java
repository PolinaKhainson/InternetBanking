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
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.bean.AccountCreditCardsBean;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of user accounts command.
 * 
 * @author P.Khainson
 * 
 */
public class ListUserAccountsCommand extends Command {

	private static final long serialVersionUID = 1187225769339374647L;
	private static final Logger LOG = Logger
			.getLogger(ListUserAccountsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.debug("Request parameter: session --> " + session);
		// obtain user from session attribute
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);
		// get user accounts from DB
		List<Account> userAccounts = DBManager.getInstance().findAccounts(user);
		LOG.trace("Found in DB: userAccounts --> " + userAccounts);
		// obtain list of accounts with credit cards assigned for each account
		List<AccountCreditCardsBean> accountCreditCardsBeanList = new ArrayList<AccountCreditCardsBean>();
		for (Account account : userAccounts) {
			AccountCreditCardsBean bean = new AccountCreditCardsBean();
			// find all credit cards for account
			List<CreditCard> creditCardsList = DBManager.getInstance()
					.findCreditCards(account);
			LOG.trace("Found in DB: creditCardsList  --> " + creditCardsList);
			bean.setAccount(account);
			bean.setCreditCardslist(creditCardsList);
			accountCreditCardsBeanList.add(bean);
		}
		// sort accounts with their credit cards by account id
		Collections.sort(accountCreditCardsBeanList,
				new Comparator<AccountCreditCardsBean>() {
					@Override
					public int compare(AccountCreditCardsBean o1,
							AccountCreditCardsBean o2) {
						return (int) (o1.getAccount().getId() - o2.getAccount()
								.getId());
					}
				});
		// put list of accounts with their credit cards to session attribute
		session.setAttribute("accountCreditCardsBeanList",
				accountCreditCardsBeanList);
		LOG.trace("Set the session attribute: accountCreditCardsBeanList --> "
				+ accountCreditCardsBeanList);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_ACCOUNTS;
	}

}
