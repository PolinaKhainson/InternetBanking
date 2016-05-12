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
import ua.nure.khainson.SummaryTask4.db.DBManager;
import ua.nure.khainson.SummaryTask4.db.bean.UserPaymentBean;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of user payments command.
 * 
 * @author P.Khainson
 * 
 */
public class ListUserPaymentsCommand extends Command {

	private static final long serialVersionUID = -31905240909639044L;
	private static final Logger LOG = Logger
			.getLogger(ListUserPaymentsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.trace("Request attribute: request --> " + request);
		// obtain user from session attribute
		User user = (User) session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + user);
		// get payments list from DB
		List<UserPaymentBean> userPaymentBeanList = DBManager.getInstance()
				.getUserPaymentBeans(user);
		for (UserPaymentBean bean : userPaymentBeanList) {
			bean.setId(user.getId());
		}
		LOG.trace("Found in DB: userPaymentBeanList --> " + userPaymentBeanList);

		String sorted = request.getParameter("sorted");
		LOG.trace("Request parameter: sorted --> " + sorted);
		// if sorted == null or emty, sort payments by payment id
		if (sorted == null || sorted.isEmpty()) {
			Collections.sort(userPaymentBeanList,
					new Comparator<UserPaymentBean>() {
						public int compare(UserPaymentBean o1,
								UserPaymentBean o2) {
							return (int) (o1.getPaymentId() - o2.getPaymentId());
						}
					});
		}
		// put payments to the request
		session.setAttribute("userPaymentBeanList", userPaymentBeanList);
		LOG.trace("Set session attribute: userPaymentBeanList --> "
				+ userPaymentBeanList);
		LOG.debug("Command finished");
		return Path.PAGE_LIST_USER_PAYMENTS;
	}

}
