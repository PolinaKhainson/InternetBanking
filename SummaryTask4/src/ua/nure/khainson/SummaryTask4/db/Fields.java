package ua.nure.khainson.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author P.Khainson
 * 
 */
public final class Fields {

	// entities
	public static final String ENTITY_ID = "id";

	public static final String USER_LOCK_STATUS_ID = "lock_status_id";
	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_EMAIL = "email";

	public static final String ACCOUNT_USER_ID = "user_id";
	public static final String ACCOUNT_NUMBER = "account_number";
	public static final String ACCOUNT_LOCK_STATUS_ID = "lock_status_id";
	public static final String ACCOUNT_SUM_ON_ACCOUNT = "sum_on_account";

	public static final String CREDIT_CARD_USER_ID = "user_id";
	public static final String CREDIT_CARD_NUMBER = "credit_card_number";
	public static final String CREDIT_CARD_ENDING_DATE = "ending_date";

	public static final String PAYMENT_CREDIT_CARD_ID = "credit_card_id";
	public static final String PAYMENT_ACCOUNT_ID = "account_id";
	public static final String PAYMENT_STATUS_ID = "status_id";
	public static final String PAYMENT_SUM_OF_MONEY = "sum_of_money";
	public static final String PAYMENT_DATE_TIME = "payment_date_time";

	// beans
	public static final String USER_PAYMENT_BEAN_CARD_NUMBER = "credit_card_number";
	public static final String USER_PAYMENT_BEAN_ACCOUNT_NUMBER = "account_number";
	public static final String USER_PAYMENT_BEAN_ACCOUNT_STATUS = "lock_status_id";
	public static final String USER_PAYMENT_BEAN_SUM_OF_MONEY = "sum_of_money";
	public static final String USER_PAYMENT_BEAN_PAYMENT_ID = "p.id";
	public static final String USER_PAYMENT_BEAN_PAYMENT_STATUS_NAME = "name";
	public static final String USER_PAYMENT_BEAN_PAYMENT_DATE = "payment_date_time";
}