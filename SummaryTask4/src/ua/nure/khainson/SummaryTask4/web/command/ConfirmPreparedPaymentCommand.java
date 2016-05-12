package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;

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
 * Confirm of already prepared payment command.
 * 
 * @author P.Khainson
 * 
 */
public class ConfirmPreparedPaymentCommand extends Command {

	private static final long serialVersionUID = -3439611057994337943L;

	private static final Logger LOG = Logger
			.getLogger(ConfirmPreparedPaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		String id = request.getParameter("paymentId");
		LOG.trace("Request pararmetr: paymentId --> " + id);
		Long paymentId = Long.parseLong(id);
		// obtain prepared payment from DB
		DBManager manager = DBManager.getInstance();
		Payment payment = manager.findPayment(paymentId);
		LOG.trace("Find from DB: payment --> " + payment);

		Long accountId = payment.getAccountId();
		LOG.trace("Obtain from payment: accountId --> " + accountId);
		BigDecimal sumOfMoney = payment.getSumOfMoney();
		// obtain account for payment from DB
		Account account = manager.findAccount(accountId);
		LOG.trace("Find from DB: account --> " + account);

		String forward = Path.PAGE_ERROR_PAGE;
		// check if enough money for confirm prepared payment
		if (account.getSumOnAccount().compareTo(sumOfMoney) >= 0) {
			LOG.trace("Sum on account > sum of payment.");
			// calculate sum on account after confirmation
			BigDecimal result = account.getSumOnAccount().subtract(sumOfMoney);
			LOG.trace("Result sum on account after confirmation: result --> "
					+ result);
			account.setSumOnAccount(result);
			LOG.trace("Account sum of money --> " + result);
			// update account with new sum on account in DB
			manager.updateAccount(account);
			LOG.trace("Update in DB: account --> " + account);

			payment.setPaymentStatusId(PaymentStatus.CONFIRMED.ordinal());
			LOG.trace("Payment status changed to --> "
					+ PaymentStatus.getPaymentStatus(payment).getName());
			// update payment with confirmed status in DB
			manager.updatePayment(payment);
			LOG.trace("Update in DB: payment --> " + payment);

		} else {
			// Show error message if not enough money on account for confirm
			// payment
			request.setAttribute("error",
					Messages.ERR_NOT_ENOUGH_MONEY_ON_ACCOUNT);
			LOG.trace("Error: Messages.ERR_NOT_ENOUGH_MONEY_ON_ACCOUNT");

			request.setAttribute("sumOnAccount", account.getSumOnAccount());
			LOG.error("Request attribute --> sumOnAccount"
					+ account.getSumOnAccount());
			request.setAttribute("paymentId", paymentId);
			LOG.error("Request attribute --> paymentId" + paymentId);
		}
		// execute command and get forward address
		Command command = CommandContainer.get("listUserPayments");
		LOG.trace("Obtained command --> " + command);
		try {
			forward = command.execute(request, response);
			LOG.trace("Forward address --> " + forward);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			LOG.error("Request attribute --> errorMessage" + ex.getMessage());
		}

		LOG.debug("Command finished");
		return forward;

	}

}
