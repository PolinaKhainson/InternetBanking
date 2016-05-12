package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListAccountsForSelectedCreditCardCommandTest {

	@Test
	public void test() {
		ListAccountsForSelectedCreditCardCommand command = new ListAccountsForSelectedCreditCardCommand();
		assertNotNull(command);
	}

}
