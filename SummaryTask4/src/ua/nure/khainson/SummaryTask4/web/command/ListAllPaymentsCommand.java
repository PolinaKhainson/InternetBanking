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
import ua.nure.khainson.SummaryTask4.db.bean.UserPaymentBean;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.exception.AppException;

/**
 * List of all payments command.
 * 
 * @author P.Khainson
 * 
 */
public class ListAllPaymentsCommand extends Command {

	private static final long serialVersionUID = 7534095456454444477L;
	private static final Logger LOG = Logger
			.getLogger(ListAllPaymentsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		LOG.debug(" Request parameter: session --> " + session);

		DBManager manager = DBManager.getInstance();
		List<UserPaymentBean> allUserPaymentBeanList = new ArrayList<UserPaymentBean>();
		// obtain users list from db
		List<User> users = manager.findUsers();
		LOG.debug("Find in DB: users --> " + users);

		for (User user : users) {
			// get payments list for each user in DB
			List<UserPaymentBean> userPaymentBeanList = manager
					.getUserPaymentBeans(user);
			LOG.trace("Found in DB: userPaymentBeanList --> "
					+ userPaymentBeanList);
			for (UserPaymentBean bean : userPaymentBeanList) {
				bean.setId(user.getId());
			}
			// fill all payments list by each users paymnets
			allUserPaymentBeanList.addAll(userPaymentBeanList);
		}
		LOG.trace("Found in DB: payments --> " + allUserPaymentBeanList);

		// sort all payments by payment id
		Collections.sort(allUserPaymentBeanList,
				new Comparator<UserPaymentBean>() {
					public int compare(UserPaymentBean o1, UserPaymentBean o2) {
						return (int) (o1.getPaymentId() - o2.getPaymentId());
					}
				});

		// put all payments list to session attribute
		session.setAttribute("allUserPaymentBeanList", allUserPaymentBeanList);
		LOG.trace("Set the session attribute: allUserPaymentBeanList --> "
				+ allUserPaymentBeanList);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_ALL_PAYMENTS;
	}

}
