package ua.nure.khainson.SummaryTask4.web.command;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.Path;
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.PaymentStatus;
import ua.nure.khainson.SummaryTask4.db.entity.Payment;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Prepare payment command.
 * 
 * @author P.Khainson
 * 
 */
public class PreparePaymentCommand extends Command {

	private static final long serialVersionUID = 6282344762918776764L;
	private static final Logger LOG = Logger
			.getLogger(PreparePaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		LOG.trace("Get session from request: session --> " + session);

		// obtain cardId, accountId and Sum of money for payment from request
		Long cardId = (Long) request.getAttribute("selectedCardId");
		LOG.trace("REquest attribute: cardId --> " + cardId);
		Long accountId = (Long) request.getAttribute("selectedAccountId");
		LOG.trace("REquest attribute: accountId --> " + accountId);
		BigDecimal sumOfMoney = (BigDecimal) request.getAttribute("sumOfMoney");
		LOG.trace("REquest attribute: sumOfMoney --> " + sumOfMoney);
		// create payment with parameters from request
		Payment payment = new Payment();
		payment.setCreditCardId(cardId);
		payment.setAccountId(accountId);
		payment.setPaymentStatusId(PaymentStatus.PREPARED.ordinal());
		payment.setSumOfMoney(sumOfMoney);
		payment.setDateTime(new Timestamp(System.currentTimeMillis()));
		LOG.debug("Create payment: payment --> " + payment);
		// create prepared payment in DB
		DBManager.getInstance().createPayment(payment);
		LOG.trace("Add to DB: payments --> " + payment);

		// execute command and get forward address
		String forward = Path.PAGE_ERROR_PAGE;
		Command command = CommandContainer.get("listUserPayments");
		try {
			forward = command.execute(request, response);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Command finished");
		return forward;
	}

}
