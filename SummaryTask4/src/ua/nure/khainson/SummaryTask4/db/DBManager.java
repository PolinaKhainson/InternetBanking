package ua.nure.khainson.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.db.bean.UserPaymentBean;
import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.Payment;
import ua.nure.khainson.SummaryTask4.exception.DBException;
import ua.nure.khainson.SummaryTask4.exception.Messages;
import ua.nure.khainson.SummaryTask4.db.entity.User;
import ua.nure.khainson.SummaryTask4.db.Fields;

/**
 * DB manager. Works with Apache Derby DB. Only the required DAO methods are
 * defined!
 * 
 * @author P.Khainson
 * 
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private DataSource ds;

	private static DBManager instance;

	// //////////////////////////////////////////////////////////
	// Singleton
	// //////////////////////////////////////////////////////////
	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/sumtask4db");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	// ////////////////FOR USER////////////////

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users "
			+ "WHERE login=?";

	private static final String SQL_INSERT_USER = "INSERT INTO users "
			+ "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_USER = "UPDATE users SET lock_status_id = ?,"
			+ " password =?, first_name=?, last_name=? WHERE id=?";

	private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

	// ////////////////FOR PAYMENT////////////////

	private static final String SQL_FIND_ALL_PAYMENTS = "SELECT * FROM payments";

	private static final String SQL_FIND_PAYMENT_BY_ID = "SELECT * FROM payments "
			+ "WHERE id = ?";

	private static final String SQL_CREATE_PAYMENT = "INSERT INTO payments "
			+ "(credit_card_id , account_id, status_id, sum_of_money, payment_date_time)"
			+ "	VALUES (?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_PAYMENT = "UPDATE payments"
			+ " SET status_id = ? WHERE id = ?";

	// ////////////////FOR ACCOUNT////////////////

	private static final String SQL_INSERT_ACCOUNT = "INSERT INTO accounts "
			+ "VALUES(DEFAULT, ?, ?, ?, ?)";
	private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM accounts";

	private static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM accounts where id = ?";
	
	private static final String SQL_GET_ACCOUNT_BY_NUMBER = "SELECT * FROM accounts where account_number = ?";
	
	private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE accounts"
			+ " SET user_id = ?, lock_status_id = ?, account_number = ?, "
			+ "sum_on_account = ?  WHERE id = ?";

	private static final String SQL_GET_ACCOUNT_BY_USER_ID = "SELECT * FROM accounts "
			+ "WHERE user_id = ?";

	private static final String SQL_FIND_ACCOUNTS_BY_CREDIT_CARD = "SELECT ca.credit_card_id,"
			+ " a.id, a.user_id, a.account_number, a.lock_status_id, a.sum_on_account"
			+ " from credit_cards_accounts ca join accounts a"
			+ " on(ca.account_id = a.id) WHERE credit_card_id = ?";
	private static final String SQL_INSERT_ACCOUNT_CREDIT_CARD = "INSERT INTO " 
			+ "credit_cards_accounts VALUES(DEFAULT, ?, ?)";

	// ////////////////FOR CREDIT CARD////////////////////

	private static final String SQL_FIND_CREDIT_CARD_BY_ID = "SELECT * FROM credit_cards "
			+ "where id = ?";
	
	private static final String SQL_FIND_CREDIT_CARD_BY_NUMBER = "SELECT * FROM credit_cards "
			+ "where credit_card_number = ?";
	
	private static final String SQL_INSERT_CREDIT_CARD = "INSERT INTO credit_cards "
			+ "VALUES(DEFAULT, ?, ?, ?)";

	private static final String SQL_FIND_CREDIT_CARDS_BY_USER_ID = "SELECT * FROM"
			+ " credit_cards WHERE user_id = ?";

	private static final String SQL_FIND_CREDIT_CARDS_BY_ACCOUNT = "SELECT ca.account_id,"
			+ " cc.id, cc.user_id, cc.credit_card_number, cc.ending_date"
			+ " from credit_cards_accounts ca join credit_cards cc"
			+ " on(ca.credit_card_id = cc.id) WHERE account_id = ?";

	// ////////////////FOR USER PAYMENT BEAN////////////////

	private static final String SQL_GET_USER_PAYMENT_BEANS_BY_USER = "SELECT p.id, "
			+ "c.credit_card_number, a.account_number, a.lock_status_id, ps.name, "
			+ "p.sum_of_money, p.payment_date_time FROM payments p, credit_cards c, "
			+ "accounts a, payment_statuses ps "
			+ "WHERE c.user_id =? AND p.credit_card_id=c.id "
			+ "AND p.account_id=a.id AND p.status_id = ps.id";

	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in
	 * your WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return DB connection.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	// //////////////////////////////////////////////////////////
	// Methods to obtain beans
	// //////////////////////////////////////////////////////////
	/**
	 * Returns all UserPaymentsBeans.
	 * 
	 * @return List of UserPaymentsBean beans.
	 * @exception DBException
	 */
	public List<UserPaymentBean> getUserPaymentBeans(User user)
			throws DBException {
		List<UserPaymentBean> userPaymentBeanList = new ArrayList<UserPaymentBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_PAYMENT_BEANS_BY_USER);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userPaymentBeanList.add(extractUserPaymentBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT_BEANS_BY_USER, ex);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT_BEANS_BY_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return userPaymentBeanList;
	}

	// //////////////////////////////////////////////////////////
	// Etity access methods
	// //////////////////////////////////////////////////////////

	// //////////////////////////FOR PAYMENT/////////////////////
	/**
	 * Creates payment in DB.
	 * 
	 * Returns true if payment is successfully created,
	 * 
	 * @return true, if payment was created in DB, false - if creation failed.
	 */
	public boolean createPayment(Payment payment) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_PAYMENT);
			int k = 0;
			pstmt.setLong(++k, payment.getCreditCardId());
			pstmt.setLong(++k, payment.getAccountId());
			pstmt.setInt(++k, payment.getPaymentStatusId());
			pstmt.setBigDecimal(++k, payment.getSumOfMoney());
			pstmt.setTimestamp(++k, payment.getDateTime());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_PAYMENT, ex);
			throw new DBException(Messages.ERR_CANNOT_CREATE_PAYMENT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Returns payment by payment id.
	 * 
	 * @param paymentId
	 *            Long id of payment.
	 * @return Payment entity.
	 */
	public Payment findPayment(Long paymentId) throws DBException {
		Payment payment = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_PAYMENT_BY_ID);
			pstmt.setLong(1, paymentId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment = extractPayment(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_PAYMENT_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_PAYMENT_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return payment;
	}

	/**
	 * Updates payment in DB. Returns true if payment is successfully updated.
	 * 
	 * @param payment
	 *            Payment entity.
	 * 
	 * @return true, if payment was updated in DB, false - if update failed.
	 */
	public boolean updatePayment(Payment payment) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_PAYMENT);
			pstmt.setInt(1, payment.getPaymentStatusId());
			pstmt.setLong(2, payment.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_PAYMENT, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_PAYMENT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Returns all payments.
	 * 
	 * @return List of payment entities.
	 */
	public List<Payment> findPayments() throws DBException {
		List<Payment> orderUserBeanList = new ArrayList<Payment>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_PAYMENTS);
			while (rs.next()) {
				orderUserBeanList.add(extractPayment(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_PAYMENTS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_PAYMENTS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return orderUserBeanList;
	}

	// //////////////////////////FOR ACCOUNT/////////////////////
	/**
	 * Returns Account by account id.
	 * 
	 * @param accountId
	 *            Long id of account.
	 * 
	 * @return Account entity
	 */
	public Account findAccount(Long accountId) throws DBException {
		Account account = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_ACCOUNT_BY_ID);
			pstmt.setLong(1, accountId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = extractAccount(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_ACCOUNT_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_ACCOUNT_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return account;
	}

	
	/**
	 * Returns Account by account number
	 * 
	 * @param accountNumber
	 *            Long number of account.
	 * 
	 * @return Account entity
	 */
	public Account findAccountByNumber(int accountNumber) throws DBException {
		Account account = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_ACCOUNT_BY_NUMBER);
			pstmt.setLong(1, accountNumber);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = extractAccount(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_ACCOUNT_BY_NUMBER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_ACCOUNT_BY_NUMBER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return account;
	}

	/**
	 * Insert account.
	 * 
	 * @param account
	 *            account to insert.
	 * @throws DBException
	 */
	public void insertAccount(Account account) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ACCOUNT);
			int k = 1;
			pstmt.setLong(k++, account.getUserId());
			pstmt.setInt(k++, account.getLockStatusId());
			pstmt.setInt(k++, account.getAccountNumber());
			pstmt.setBigDecimal(k++, account.getSumOnAccount());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_ACCOUNT, ex);
		} finally {
			close(con, pstmt, null);
		}
	}
	/**
	 * Updates account in DB.
	 * 
	 * @param account
	 *            Account entity
	 * 
	 */
	public void updateAccount(Account account) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
			pstmt.setLong(1, account.getUserId());
			pstmt.setInt(2, account.getLockStatusId());
			pstmt.setInt(3, account.getAccountNumber());
			pstmt.setBigDecimal(4, account.getSumOnAccount());
			pstmt.setLong(5, account.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_ACCOUNT_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ACCOUNT_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Returns all accounts for User.
	 * 
	 * @param user
	 *            User entity
	 * 
	 * @return List of accounts entities.
	 */
	public List<Account> findAccounts(User user) throws DBException {
		List<Account> accountList = new ArrayList<Account>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_ACCOUNT_BY_USER_ID);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				accountList.add(extractAccount(rs));
			}
//			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ACOOUNTS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ACOOUNTS, ex);
		} finally {
			close(con, pstmt, rs);
		}
	
		return accountList;
	}

	/**
	 * Returns all accounts by credit card id.
	 * 
	 * @param creditCardId
	 *            Long id of credit card.
	 * 
	 * @return List of account entities for user.
	 */
	public List<Account> findAccounts(Long creditCardId) throws DBException {
		List<Account> accountList = new ArrayList<Account>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ACCOUNTS_BY_CREDIT_CARD);
			pstmt.setLong(1, creditCardId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				accountList.add(extractAccount(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_CREDIT_CARD, ex);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_CREDIT_CARD, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return accountList;
	}

	/**
	 * Returns all accounts.
	 * 
	 * @return List of account entities.
	 */
	public List<Account> findAccounts() throws DBException {
		List<Account> accountsList = new ArrayList<Account>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_ACCOUNTS);
			while (rs.next()) {
				accountsList.add(extractAccount(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_ACCOUNTS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_ACCOUNTS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return accountsList;
	}

	// //////////////////////////FOR CREDIT CARDS/////////////////////
	/**
	 * Insert credit card.
	 * 
	 * @param creditCard
	 *            credit card to insert.
	 * @throws DBException
	 */
	public void insertCreditCard(CreditCard creditCard) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_CREDIT_CARD);
			int k = 1;
			pstmt.setLong(k++, creditCard.getUserId());
			pstmt.setInt(k++, creditCard.getCardNumber());
			pstmt.setDate(k++, creditCard.getEndingDate());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_CREDIT_CARD, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_CREDIT_CARD, ex);
		} finally {
			close(con, pstmt, null);
		}
	}
	
	/**
	 * Returns all credit cards assigned to account.
	 * 
	 * @param account
	 *            Account entity
	 * 
	 * @return List of credit card entities.
	 */
	public List<CreditCard> findCreditCards(Account account) throws DBException {
		List<CreditCard> creditCardList = new ArrayList<CreditCard>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CREDIT_CARDS_BY_ACCOUNT);
			pstmt.setLong(1, account.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				creditCardList.add(extractCreditCard(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_ACCOUNT, ex);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_ACCOUNT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return creditCardList;
	}

	/**
	 * Returns all credit cards for user.
	 * 
	 * @param user
	 *            User entity
	 * 
	 * @return List of credit card entities.
	 */
	public List<CreditCard> findCreditCards(User user) throws DBException {
		List<CreditCard> creditCardList = new ArrayList<CreditCard>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CREDIT_CARDS_BY_USER_ID);
			pstmt.setLong(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				creditCardList.add(extractCreditCard(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_USER_ID, ex);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_USER_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return creditCardList;
	}

	/**
	 * Returns credit card by id
	 * 
	 * @param Long
	 *            Credit card id
	 * 
	 * @return CreditCard entity.
	 */
	public CreditCard findCreditCard(Long cardId) throws DBException {
		CreditCard creditCard = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CREDIT_CARD_BY_ID);
			pstmt.setLong(1, cardId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				creditCard = extractCreditCard(rs);
			}
			con.commit();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return creditCard;
	}

	
	/**
	 * Returns credit card by credit card number
	 * 
	 * @param Long
	 *            Credit card number
	 * 
	 * @return CreditCard entity.
	 */
	public CreditCard findCreditCardByNumber(int cardNumber) throws DBException {
		CreditCard creditCard = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CREDIT_CARD_BY_NUMBER);
			pstmt.setLong(1, cardNumber);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				creditCard = extractCreditCard(rs);
			}
			con.commit();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_NUMBER, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_NUMBER,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return creditCard;
	}
	/**
	 * Insert account id and credit card id to accounts_credit_cards
	 * 
	 * @param Long
	 *            Credit card id
	 * 
	 * @return CreditCard entity.
	 */
	public void insertAccountIdCreditCardId(Long accountId, Long cardId) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ACCOUNT_CREDIT_CARD);
			int k = 1;
			pstmt.setLong(k++, cardId);
			pstmt.setLong(k++, accountId);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_ACCOUNT_CREDIT_CARD, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_ACCOUNT_CREDIT_CARD, ex);
		} finally {
			close(con, pstmt, null);
		}
	}
	// //////////////////////////FOR USER/////////////////////
	/**
	 * Insert user.
	 * 
	 * @param user
	 *            user to insert.
	 * @throws DBException
	 */
	public void insertUser(User user) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_USER);
			int k = 1;
			pstmt.setInt(k++, user.getLockStatusId());
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setString(k++, user.getEmail());
			pstmt.setInt(k++, user.getRoleId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
		} finally {
			close(con, pstmt, null);
		}
	}

	/**
	 * Delete user.
	 * 
	 * @param user
	 *            user to delete.
	 * @throws DBException
	 */
	public void deleteUser(Long userId) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER_BY_ID);
			pstmt.setLong(1, userId);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, null);
		}
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given id.
	 * 
	 * @param id
	 *            User id.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUser(long userId) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setLong(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns all Users
	 * 
	 * @return List of user entities.
	 */
	public List<User> findUsers() throws DBException {
		List<User> usersList = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				usersList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return usersList;
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws DBException
	 */
	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws SQLException
	 */
	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setInt(k++, user.getLockStatusId());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setLong(k, user.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	// //////////////////////////////////////////////////////////
	// Other methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a user payment bean from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user payment bean will be extracted.
	 * @return UserPaymentBean object
	 */

	private UserPaymentBean extractUserPaymentBean(ResultSet rs)
			throws SQLException {
		UserPaymentBean userPaymentBean = new UserPaymentBean();
		userPaymentBean.setCreditCardNumber(rs
				.getInt(Fields.USER_PAYMENT_BEAN_CARD_NUMBER));
		userPaymentBean.setAccountNumber(rs
				.getInt(Fields.USER_PAYMENT_BEAN_ACCOUNT_NUMBER));
		userPaymentBean.setAccountStatus(rs
				.getInt(Fields.USER_PAYMENT_BEAN_ACCOUNT_STATUS));
		userPaymentBean.setSumOfMoney(rs
				.getBigDecimal(Fields.USER_PAYMENT_BEAN_SUM_OF_MONEY));
		userPaymentBean.setPaymentId(rs.getLong(Fields.ENTITY_ID));
		userPaymentBean.setDateOfPayment(rs
				.getDate(Fields.USER_PAYMENT_BEAN_PAYMENT_DATE));
		userPaymentBean.setTimeOfPayment(rs
				.getTime(Fields.USER_PAYMENT_BEAN_PAYMENT_DATE));
		userPaymentBean.setPaymentStatusName(rs
				.getString(Fields.USER_PAYMENT_BEAN_PAYMENT_STATUS_NAME));
		LOG.trace("extracted userPaymentBean ==> " + userPaymentBean);
		return userPaymentBean;
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setLockStatusId(rs.getInt(Fields.USER_LOCK_STATUS_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		LOG.trace("extracted user ==> " + user);
		return user;
	}

	/**
	 * Extracts a payment entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a payment entity will be extracted.
	 * @return Payment entity
	 */
	private Payment extractPayment(ResultSet rs) throws SQLException {
		Payment payment = new Payment();
		payment.setId(rs.getLong(Fields.ENTITY_ID));
		payment.setCreditCardId(rs.getLong(Fields.PAYMENT_CREDIT_CARD_ID));
		payment.setAccountId(rs.getLong(Fields.PAYMENT_ACCOUNT_ID));
		payment.setPaymentStatusId(rs.getInt(Fields.PAYMENT_STATUS_ID));
		payment.setSumOfMoney(rs.getBigDecimal(Fields.PAYMENT_SUM_OF_MONEY));
		payment.setDateTime(rs.getTimestamp(Fields.PAYMENT_DATE_TIME));
		LOG.trace("extracted payment ==> " + payment);
		return payment;
	}

	/**
	 * Extracts a account entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a account entity will be extracted.
	 * @return Account entity
	 */
	private Account extractAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setId(rs.getLong(Fields.ENTITY_ID));
		account.setUserId(rs.getLong(Fields.ACCOUNT_USER_ID));
		account.setAccountNumber(rs.getInt(Fields.ACCOUNT_NUMBER));
		account.setLockStatusId(rs.getInt(Fields.ACCOUNT_LOCK_STATUS_ID));
		account.setSumOnAccount(rs.getBigDecimal(Fields.ACCOUNT_SUM_ON_ACCOUNT));
		LOG.trace("extracted account ==> " + account);
		return account;
	}

	/**
	 * Extracts a credit card entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a credit card entity will be extracted.
	 * @return Credit card entity
	 */
	private CreditCard extractCreditCard(ResultSet rs) throws SQLException {
		CreditCard creditCard = new CreditCard();
		creditCard.setId(rs.getLong(Fields.ENTITY_ID));
		creditCard.setUserId(rs.getLong(Fields.CREDIT_CARD_USER_ID));
		creditCard.setCardNumber(rs.getInt(Fields.CREDIT_CARD_NUMBER));
		creditCard.setEndingDate(rs.getDate(Fields.CREDIT_CARD_ENDING_DATE));
		LOG.trace("extracted credit card ==> " + creditCard);
		return creditCard;
	}

}
