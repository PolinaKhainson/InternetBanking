package ua.nure.khainson.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author P.Khainson
 * 
 */
public final class Path {

	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_REGISTRATION = "/registration.jsp";
	public static final String PAGE_SUCCESSFUL_REGISTRATION = "/successful_registration.jsp";
	// client
	public static final String PAGE_LIST_USER_PAYMENTS = "/WEB-INF/jsp/client/payments.jsp";
	public static final String PAGE_LIST_USER_ACCOUNTS = "/WEB-INF/jsp/client/accounts.jsp";
	public static final String PAGE_CREATE_PAYMENT = "/WEB-INF/jsp/client/create_payment.jsp";
	public static final String CLIENT_PAGES_PATH = "/WEB-INF/jsp/client/";
	// admin
	public static final String PAGE_LIST_USERS = "/WEB-INF/jsp/admin/users.jsp";
	public static final String PAGE_LIST_ALL_PAYMENTS = "/WEB-INF/jsp/admin/all_payments.jsp";
	public static final String PAGE_LIST_ALL_ACCOUNTS = "/WEB-INF/jsp/admin/all_accounts.jsp";
	public static final String PAGE_CREATE_ACCOUNT = "/WEB-INF/jsp/admin/create_account.jsp";
	public static final String PAGE_CREATE_CREDIT_CARD = "/WEB-INF/jsp/admin/create_credit_card.jsp";
	public static final String ADMIN_PAGES_PATH = "/WEB-INF/jsp/admin/";
	// common
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String COMMON_PAGES_PATH = "/WEB-INF/jsp/";
	// commands
	public static final String COMMAND_LIST_USER_PAYMENTS = "/controller?command="
			+ "listUserPayments";
	public static final String COMMAND_LIST_USERS = "/controller?command=listUsers";

}