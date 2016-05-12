package ua.nure.khainson.SummaryTask4.exception;

/**
 * An exception that provides information on an application error.
 * 
 * @author P.Khainson
 * 
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 195179430468831077L;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
