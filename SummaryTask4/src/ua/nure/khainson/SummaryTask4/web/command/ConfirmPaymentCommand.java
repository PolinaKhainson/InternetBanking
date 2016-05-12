package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.PaymentStatus;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.Payment;
import ua.nure.khainson.SummaryTask4.exception.AppException;
import ua.nure.khainson.SummaryTask4.exception.Messages;

/**
 * Confirm payment command.
 * 
 * @author P.Khainson
 * 
 */
public class ConfirmPaymentCommand extends Command {

	private static final long serialVersionUID = 9216314133428360918L;
	private static final Logger LOG = Logger
			.getLogger(ConfirmPaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();

		Long paymentId = (Long) request.getAttribute("paymentId");
		LOG.trace("Request pararmetr: paymentId --> " + paymentId);

		// obtain user from session attribute
		Long cardId = (Long) request.getAttribute("selectedCardId");
		LOG.trace("Request attribute: cardId --> " + cardId);

		Long accountId = (Long) request.getAttribute("selectedAccountId");
		LOG.trace("Request attribute: accountId --> " + accountId);

		BigDecimal sumOfMoney = (BigDecimal) request.getAttribute("sumOfMoney");
		LOG.trace("Request attribute: sumOfMoney --> " + sumOfMoney);
		// create payment by obtained request parameters
		Payment payment = new Payment();
		payment.setCreditCardId(cardId);
		payment.setAccountId(accountId);
		payment.setPaymentStatusId(PaymentStatus.CONFIRMED.ordinal());
		payment.setSumOfMoney(sumOfMoney);
		payment.setDateTime(new Timestamp(System.currentTimeMillis()));
		LOG.trace("Create new object: payment --> " + payment);
		// obtain account for payment by accountId
		Account account = manager.findAccount(accountId);
		LOG.trace("Find from DB: account --> " + account);

		String forward = Path.PAGE_ERROR_PAGE;
		// validate if enough money on account to pay
		if (account.getSumOnAccount().compareTo(sumOfMoney) >= 0) {
			LOG.trace("Sum on account > sum of payment.");
			// calculate new sum on account
			BigDecimal result = account.getSumOnAccount().subtract(sumOfMoney);
			LOG.trace("Result sum on account after confirmation: result --> "
					+ result);
			account.setSumOnAccount(result);
			LOG.trace("Account sum of money --> " + result);
			// update account with new sum on account
			manager.updateAccount(account);
			LOG.trace("Update in DB: account --> " + account);

			manager.createPayment(payment);
			LOG.trace("Create in DB: payment --> " + payment);

			// execute command and get forward address
			Command command = CommandContainer.get("listUserPayments");
			LOG.trace("Obtained command --> " + command);
			try {
				forward = command.execute(request, response);
				LOG.trace("Forward address --> " + forward);
			} catch (AppException ex) {
				request.setAttribute("errorMessage", ex.getMessage());
				LOG.error("Request attribute --> errorMessage"
						+ ex.getMessage());
			}

		} else {
			// show error message if not enough money on account for pay
			request.setAttribute("error",
					Messages.ERR_NOT_ENOUGH_MONEY_ON_ACCOUNT);
			LOG.debug("Error: Messages.ERR_NOT_ENOUGH_MONEY_ON_ACCOUNT");

			request.setAttribute("sumOnAccount", account.getSumOnAccount());

			forward = Path.PAGE_CREATE_PAYMENT;
			LOG.trace("Forward address --> " + forward);

		}
		LOG.debug("Command finished");
		return forward;
	}

}
