package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import ua.nure.khainson.SummaryTask4.db.Role;
import ua.nure.khainson.SummaryTask4.db.bean.AccountCreditCardsBean;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of all accounts command.
 * 
 * @author P.Khainson
 * 
 */
public class ListAllAccountsCommand extends Command {

	private static final long serialVersionUID = 5341465583679465971L;

	private static final Logger LOG = Logger
			.getLogger(ListAllAccountsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.debug("Request parameter: session -->" + session);

		// get all accounts list
		List<Account> allAccounts = DBManager.getInstance().findAccounts();
		LOG.trace("Found in DB: payments --> " + allAccounts);
		// get list of credit cards assigned to each account from allAccounts
		// list
		List<AccountCreditCardsBean> accountCreditCardsBeanList = new ArrayList<AccountCreditCardsBean>();
		for (Account account : allAccounts) {
			AccountCreditCardsBean bean = new AccountCreditCardsBean();
			// get list of credit cards for ACCOUNT
			List<CreditCard> creditCardsList = DBManager.getInstance()
					.findCreditCards(account);
			LOG.trace("Found in DB: creditCardsList --> " + creditCardsList);

			// sort users by user id
			Collections.sort(creditCardsList, new Comparator<CreditCard>() {
				public int compare(CreditCard o1, CreditCard o2) {
					return (int) (o1.getCardNumber() - o2.getCardNumber());
				}
			});
			// list of ALL credit card for USER
			// find user in DB
			User user = DBManager.getInstance().findUser(account.getUserId());
			LOG.trace("Found in DB: creditCards --> " + creditCardsList);
			List<CreditCard> allCreditCardsList = DBManager.getInstance()
					.findCreditCards(user);
			// create map with info about users for jsp page select tag
			Map<Long, String> creditCards = new LinkedHashMap<Long, String>();
			for (int i = 0; i < allCreditCardsList.size(); i++) {
				CreditCard creditCard = allCreditCardsList.get(i);
				// find if credit card already assigned to current account
				boolean cardAssignedForAccount = false;
				for (CreditCard cc : creditCardsList) {
					if (cc.getId().equals(creditCard.getId())) {
						cardAssignedForAccount = true;
						break;
					}
				}
				// add for list only clients for whom we can assigne credit
				// card, not admins
				if (!cardAssignedForAccount) {
					creditCards.put(
							creditCard.getId(),
							creditCard.getCardNumber() + "("
									+ creditCard.getEndingDate() + ")");
				}
			}
			LOG.trace("Map of users --> " + creditCards);

			bean.setOwnerFirstName(user.getFirstName());
			bean.setOwnerLastName(user.getLastName());
			bean.setAccount(account);
			bean.setCreditCards(creditCards);
			bean.setCreditCardslist(creditCardsList);
			accountCreditCardsBeanList.add(bean);
		}
		// default sortion of accounts by account id
		Collections.sort(accountCreditCardsBeanList,
				new Comparator<AccountCreditCardsBean>() {
					@Override
					public int compare(AccountCreditCardsBean o1,
							AccountCreditCardsBean o2) {
						return (int) (o1.getAccount().getId() - o2.getAccount()
								.getId());
					}
				});

		// create Map - Users and TOTAL sum on all accounts
		Map<String, BigDecimal> userSumOfMoneyMap = new HashMap<String, BigDecimal>();
		DBManager manager = DBManager.getInstance();
		List<User> users = manager.findUsers();
		for (User user : users) {
			if (!Role.getRole(user).getName().equals("admin")) {
				BigDecimal totalSumOfMoney = new BigDecimal("0.");
				List<Account> accounts = manager.findAccounts(user);
				LOG.trace("User for second table(Total sum on account)" + user);
				LOG.trace("User accounts" + accounts);
				for (Account acc : accounts) {
					totalSumOfMoney = totalSumOfMoney
							.add(acc.getSumOnAccount());
					LOG.trace("sumOnAccount" + acc.getSumOnAccount());
				}
				LOG.trace("Total sum of money" + totalSumOfMoney);
				userSumOfMoneyMap.put(
						user.getFirstName() + " " + user.getLastName(),
						totalSumOfMoney);
			}
		}

		session.setAttribute("userSumOfMoneyMap", userSumOfMoneyMap);
		LOG.trace("Set the session attribute: accountCreditCardsBeanList --> "
				+ accountCreditCardsBeanList);
		// put accounts with assigned credit cards to session attribute
		session.setAttribute("accountCreditCardsBeanList",
				accountCreditCardsBeanList);
		LOG.trace("Set the session attribute: accountCreditCardsBeanList --> "
				+ accountCreditCardsBeanList);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_ALL_ACCOUNTS;
	}
}
