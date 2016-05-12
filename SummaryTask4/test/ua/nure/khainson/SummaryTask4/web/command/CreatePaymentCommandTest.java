package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreatePaymentCommandTest {

	@Test
	public void test() {
		CreatePaymentCommand createPaymentCommand = new CreatePaymentCommand();
		assertNotNull(createPaymentCommand);
	}

}
