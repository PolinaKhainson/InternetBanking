package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListUserPaymentsCommandTest {

	@Test
	public void test() {
		ListUserPaymentsCommand listUserPaymentsCommand = new ListUserPaymentsCommand();
		assertNotNull(listUserPaymentsCommand);
	}

}
