package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class CancelPaymentCommandTest {

	@Test
	public void test() {
		CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand();
		assertNotNull(cancelPaymentCommand);
	}

}
