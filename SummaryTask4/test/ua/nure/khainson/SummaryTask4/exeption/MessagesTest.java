package ua.nure.khainson.SummaryTask4.exeption;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.nure.khainson.SummaryTask4.exception.Messages;


public class MessagesTest {

	@Test
	public void test() {
		String message = Messages.ERR_ACCOUNT_MUST_BE_SHOSEN;
		assertNotNull(message);
	}

}
