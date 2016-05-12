package ua.nure.khainson.SummaryTask4.exception;

/**
 * An exception that provides information on a database access error.
 * 
 * @author P.Khainson
 * 
 */
public class DBException extends AppException {

	private static final long serialVersionUID = 1099302164837425048L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}
