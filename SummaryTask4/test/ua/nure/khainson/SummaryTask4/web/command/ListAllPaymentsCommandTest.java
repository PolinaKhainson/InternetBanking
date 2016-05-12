package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListAllPaymentsCommandTest {

	@Test
	public void test() {
		ListAllPaymentsCommand listAllPaymentsCommand = new ListAllPaymentsCommand();
		assertNotNull(listAllPaymentsCommand);
	}

}
