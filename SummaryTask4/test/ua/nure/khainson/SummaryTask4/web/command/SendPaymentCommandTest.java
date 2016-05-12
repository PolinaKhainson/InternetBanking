package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class SendPaymentCommandTest {

	@Test
	public void test() {
		SendPaymentCommand sendPaymentCommand = new SendPaymentCommand();
		assertNotNull(sendPaymentCommand);
	}

}
