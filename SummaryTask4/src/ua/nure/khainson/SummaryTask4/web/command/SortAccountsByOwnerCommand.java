package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
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

public class SortAccountsByOwnerCommand extends Command {

	private static final long serialVersionUID = 4171958719964122455L;
	private static final Logger LOG = Logger
			.getLogger(SendPaymentCommand.class);

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.trace("Command starts");
		
		HttpSession session = request.getSession();
		List<AccountCreditCardsBean>  accountCreditCardsBeanList = (List<AccountCreditCardsBean>) session.getAttribute("accountCreditCardsBeanList");
		// sort accounts with their assigned credit cards BY OWNER
		String orderOwner = request.getParameter("orderOwner");
		if(orderOwner != null && !orderOwner.isEmpty()) {
			if (orderOwner.equals("asc")) {
			Collections.sort(accountCreditCardsBeanList,
					new Comparator<AccountCreditCardsBean>() {
						@Override
						public int compare(AccountCreditCardsBean o1,
								AccountCreditCardsBean o2) {
							return (int) (o1.getOwnerFirstName().compareTo(o2.getOwnerFirstName()));
						}
					});
			}
			if (orderOwner != null && orderOwner.equals("desc")) {
				Collections.sort(accountCreditCardsBeanList,
						new Comparator<AccountCreditCardsBean>() {
							@Override
							public int compare(AccountCreditCardsBean o1,
									AccountCreditCardsBean o2) {
								return (int) (o2.getOwnerFirstName().compareTo(o1.getOwnerFirstName()));
							}
						});
				}
		}
		session.setAttribute("orderOwner", orderOwner);
		LOG.trace("Command finishes");
		return Path.PAGE_LIST_ALL_ACCOUNTS;
	}

}
