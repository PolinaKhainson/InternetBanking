package ua.nure.khainson.SummaryTask4.exception;

/**
 * Holder for messages of exceptions.
 * 
 * @author P.Khainson
 * 
 */
public final class Messages {

	private Messages() {

	}

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_OBTAIN_USER_PAYMENT_BEANS_BY_USER = "Cannot obtain user payment beans by user";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by login";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by id";

	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain all users";

	public static final String ERR_CANNOT_OBTAIN_ALL_PAYMENTS = "Cannot obtain all payments";

	public static final String ERR_CANNOT_OBTAIN_PAYMENTS_BY_USER = "Cannot obtain payments by user";

	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update user";

	public static final String ERR_CANNOT_DELETE_USER_BY_ID = "Cannot delete user by id";

	public static final String ERR_CANNOT_OBTAIN_USER_ACOOUNTS = "Cannot obtain user accounts";
	
	public static final String ERR_CANNOT_INSERT_CREDIT_CARD = "Cannot insert credit card";

	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID = "Canot obtain credit card by id";
	
	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_NUMBER = "Cannot obtain credit card by number";

	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_ACCOUNT = "Cannot obtain credit cards by Account";

	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_USER_ID = "Cannot obtain credit cards by user id";

	public static final String ERR_CANNOT_OBTAIN_ACCOUNTS_BY_CREDIT_CARD = "Cannot obtain accounts by credit card";

	public static final String ERR_CANNOT_CREATE_PAYMENT = "Cannot create payment";

	public static final String ERR_CANNOT_UPDATE_PAYMENT = "Cannot update payment";

	public static final String ERR_CANNOT_FIND_ACCOUNT_BY_ID = "Cannot find account by id";
	
	public static final String ERR_CANNOT_INSERT_ACCOUNT_CREDIT_CARD = "Cannot inser account and credit card";

	public static final String ERR_CANNOT_UPDATE_ACCOUNT_BY_ID = "Cannot update account by id";
	
	public static final String ERR_CANNOT_FIND_ACCOUNT_BY_NUMBER = "Cannot obtain account by number";

	public static final String ERR_CANNOT_FIND_PAYMENT_BY_ID = "Cannot find payment by id";

	public static final String ERR_CANNOT_OBTAIN_ALL_ACCOUNTS = "Cannot obtain all accounts";

	public static final String ERR_CREDIT_CARD_MUST_BE_CHOSEN = "Credit card must be chosen!";
	
	public static final String ERR_CANNOT_INSERT_USER = "Cannot insert user";
	
	public static final String ERR_CANNOT_INSERT_ACCOUNT = "Cannot insert account";

	public static final String ERR_ACCOUNT_MUST_BE_SHOSEN = "Account must be chosen!";

	public static final String ERR_SUM_OF_MONEY_FIELD_IS_EMPTY = "Sum of money field is empty!";

	public static final String ERR_INVALID_SUM_OF_MONEY = "Sum of money is invalid! "
			+ "\n Sum of money must be a number with o "
			+ "without decimal point. \nAfter decimal point there can be none, one or two signs.";

	public static final String ERR_TOO_LARGE_SUM_OF_MONEY = "Too large sum of money, max sum is 1 000 000 000 000.";

	public static final String ERR_SUM_OF_MONEY_CANNOT_EQUAL_ZERO = "Sum of money cannot be equal to 0";

	public static final String ERR_NOT_ENOUGH_MONEY_ON_ACCOUNT = "Not enough money on account to confirm payment";

	public static final String ERR_NO_CHECKED_ACCOUNTS_FOR_LOCKING = "Please, choose the account you want to lock  first!";

	public static final String ERR_NO_CHECKED_ACCOUNTS_FOR_UNLOCKING = "Please, choose the account you want to unlock  first!";

	public static final String ERR_NO_CHECKED_ACCOUNTS_FOR_ADDING_FUNDS = "Please, choose the account you want to add funds first!";

	public static final String ERR_INVALID_PASSWORD = "Invalid password.Password lenght must be from 4 to 10 signs. Password can contain numbers, enlish letters and signs: ! @ $ % ^ & *";

	public static final String ERR_INVALID_FIRST_NAME = "Check your first name. First name lenght must be less than 20 signs, First name can contain letters, spaces and sign \"-\"";

	public static final String ERR_INVALID_LAST_NAME = "Check your last name. Last name lenght must be less than 20 signs, Last name can contain letters, spaces and sign \"-\"";

	public static final String ERR_INVALID_LOGIN = "Check your login. Login lenght must be from 3 to 16 signs,login can contain letters, undersores, numbers and sign \"-\"";

	public static final String ERR_INVALID_LOGIN_USER_ALREADY_EXIST = "User with such login already exists";

	public static final String ERR_INVALID_EMAIL = "Check your email";

	public static final String ERR_SACOND_PASSWORD_IS_EMPTY = "You must confirm the password second time.";

	public static final String ERR_FIRST_PASSWORD_IS_EMPTY = "Password is empty";

	public static final String ERR_FIRST_NAME_IS_EMPTY = "First name is empty";

	public static final String ERR_LAST_NAME_IS_EMPTY = "Last name is empty";

	public static final String ERR_LOGIN_IS_EMPTY = "Login is empty";

	public static final String ERR_EMAIL_IS_EMPTY = "Email is empty";

	public static final String ERR_PASSWORD_IS_EMPTY = "Password is empty";

	public static final String ERR_PASSWORDS_NOT_THE_SAME = "Passwords in first and second field must be the same";

	public static final String ERR_ZERO_SUM_OF_MONEY = "Sum can't be equal 0 ";

	public static final String ERR_CANNOT_CREATE_A_PDF_REPORT_OF_PAYMENT = "Cannot create a pdf report for payment";
	
	public static final String ERR_USER_MUST_BE_CHOSEN = "User must be chosen";
	
	public static final String ERR_ACCOUNT_NUMBER_FIELD_IS_EMPTY = "Account number field is empty";

	public static final String ERR_INVALID_ACCOUNT_NUMBER = "Account number can consist from 6 numbers from 0 to 9.";
	
	public static final String ERR_INVALID_CREDIT_CARD_NUMBER = "Credit card number can consist from 8 numbers from 0 to 9.";

	public static final String ERR_CREDIT_CARD_NUMBER_FIELD_IS_EMPTY = "Credit card number field is empty";
	
	public static final String ERR_YEAR_MUST_BE_CHOSEN = "Year must be chosen";
	
	public static final String ERR_MONTH_MUST_BE_CHOSEN = "Month must be chosen";
	
	public static final String ERR_ASSIGNED_CAREDIT_CARD_ID_FIELD_IS_EMPTY = "Choose card to assign";

	public static final String ACCOUNT_ALREADY_EXISTS = "Account with such number already exists";

	public static final String CREDIT_CARD_ALREADY_EXISTS = "Credit card with such number already exists";
}
