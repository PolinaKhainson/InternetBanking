package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
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
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Create payment command.
 * 
 * @author P.Khainson
 * 
 */
public class CreatePaymentCommand extends Command {

	private static final long serialVersionUID = -4729369250961129363L;
	private static final Logger LOG = Logger
			.getLogger(CreatePaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		HttpSession session = request.getSession();
		LOG.trace("Get session from request: session --> " + session);

		// obtain user from session attribute
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);

		// get credit cards list for user
		List<CreditCard> userCreditCardsList = DBManager.getInstance()
				.findCreditCards(user);
		LOG.trace("Found in DB: userCreditCardsList --> " + userCreditCardsList);

		// sort credit cards by card number
		Collections.sort(userCreditCardsList, new Comparator<CreditCard>() {
			@Override
			public int compare(CreditCard o1, CreditCard o2) {
				return (int) (o1.getCardNumber() - o2.getCardNumber());
			}
		});

		// create map with info about credit cards for jsp page
		Map<Long, String> creditCards = new LinkedHashMap<Long, String>();
		for (int i = 0; i < userCreditCardsList.size(); i++) {
			CreditCard card = userCreditCardsList.get(i);
			creditCards.put(card.getId(),
					card.getCardNumber() + "/" + card.getEndingDate());
		}
		LOG.trace("Map of creditCards for user --> " + creditCards);
		// set attribute map of credit cards info to session
		session.setAttribute("creditCards", creditCards);
		LOG.trace("Session attribute: creditCards --> " + creditCards);

		forward = Path.PAGE_CREATE_PAYMENT;
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Command finished");
		return forward;
	}

}
