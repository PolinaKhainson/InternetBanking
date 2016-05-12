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
import ua.nure.khainson.SummaryTask4.db.bean.UserPaymentBean;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * Sort payments command.
 * 
 * @author P.Khainson
 * 
 */
public class SortPaymentsCommand extends Command {
	private static final long serialVersionUID = -5205293064938896323L;
	private static final Logger LOG = Logger
			.getLogger(SortPaymentsCommand.class);

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Request parameter: session --> " + session);

		// obtain payments for user from session attribute
		List<UserPaymentBean> userPaymentBeanList = (List<UserPaymentBean>) session
				.getAttribute("userPaymentBeanList");
		LOG.trace("Get from session: userPaymentBeanList --> "
				+ userPaymentBeanList);
		// get witch column must be sorted
		String columnName = request.getParameter("columnName");
		LOG.trace("Get from request: columnName --> " + columnName);
		// orderPaymentNumber != null if order number column of payment table
		// has been chosen
		String orderPaymentNumber = request.getParameter("orderPaymentNumber");
		LOG.trace("Get from request: orderPaymentNumber --> "
				+ orderPaymentNumber);
		// orderInitPaymentNumber != null if INIT order number column of payment
		// table has been chosen
		String orderInitPaymentNumber = request
				.getParameter("orderInitPaymentNumber");
		LOG.trace("Get from request: orderInitPaymentNumber --> "
				+ orderInitPaymentNumber);
		// orderPaymentDate != null if date column of payment table has been
		// chosen
		String orderPaymentDate = request.getParameter("orderPaymentDate");
		LOG.trace("Get from request: orderPaymentDate --> " + orderPaymentDate);
		// columnName = orderPaymentNumber - just reverse order in list of
		// payments
		if (columnName.equals("orderPaymentNumber")) {
			List<UserPaymentBean> list = new ArrayList<UserPaymentBean>();
			for (int i = userPaymentBeanList.size() - 1; i >= 0; i--) {
				list.add(userPaymentBeanList.get(i));
			}
			userPaymentBeanList = list;
		}
		// columnName = initPaymentNumber and order = desc - sort payments list
		// desc by payment id
		if (columnName.equals("initPaymentNumber")
				&& orderInitPaymentNumber.equals("desc")) {
			Collections.sort(userPaymentBeanList,
					new Comparator<UserPaymentBean>() {
						public int compare(UserPaymentBean o1,
								UserPaymentBean o2) {
							return -(int) (o1.getPaymentId() - o2
									.getPaymentId());
						}
					});
		}
		// columnName = initPaymentNumber and order = asc - sort payments list
		// asc by payment id
		if (columnName.equals("initPaymentNumber")
				&& orderInitPaymentNumber.equals("asc")) {
			Collections.sort(userPaymentBeanList,
					new Comparator<UserPaymentBean>() {
						public int compare(UserPaymentBean o1,
								UserPaymentBean o2) {
							return (int) (o1.getPaymentId() - o2.getPaymentId());
						}
					});
		}
		// columnName = paymentDate and order = desc - sort payments list desc
		// by payment date
		if (columnName.equals("paymentDate") && orderPaymentDate.equals("desc")) {
			Collections.sort(userPaymentBeanList,
					new Comparator<UserPaymentBean>() {
						public int compare(UserPaymentBean o1,
								UserPaymentBean o2) {
							int dateResult = o1.getDateOfPayment().compareTo(
									o2.getDateOfPayment());
							if (dateResult != 0) {
								return dateResult;
							} else {
								return (o1.getTimeOfPayment().compareTo(o2
										.getTimeOfPayment()));
							}
						}
					});
		}
		// columnName = paymentDate and order = asc - sort payments list asc by
		// payment date
		if (columnName.equals("paymentDate") && orderPaymentDate.equals("asc")) {
			Collections.sort(userPaymentBeanList,
					new Comparator<UserPaymentBean>() {
						public int compare(UserPaymentBean o1,
								UserPaymentBean o2) {
							int dateResult = o2.getDateOfPayment().compareTo(
									o1.getDateOfPayment());
							if (dateResult != 0) {
								return dateResult;
							} else {
								return (o2.getTimeOfPayment().compareTo(o1
										.getTimeOfPayment()));
							}
						}

					});
		}

		// set payments sorted list to session attribute
		session.setAttribute("userPaymentBeanList", userPaymentBeanList);
		LOG.trace("Set the request attribute: userPaymentBeanList --> "
				+ userPaymentBeanList);
		// set current order of column PaymentNumber to session attribute
		session.setAttribute("orderPaymentNumber", orderPaymentNumber);
		LOG.trace("Set the request attribute: orderPaymentNumber --> "
				+ orderPaymentNumber);
		// set current order of column InitPaymentNumber to session attribute
		session.setAttribute("orderInitPaymentNumber", orderInitPaymentNumber);
		LOG.trace("Set the request attribute: orderInitPaymentNumber --> "
				+ orderInitPaymentNumber);
		// set current order of column Payment to session attribute
		session.setAttribute("orderPaymentDate", orderPaymentDate);
		LOG.trace("Set the request attribute: orderPaymentDate --> "
				+ orderPaymentDate);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_PAYMENTS;
	}
}
