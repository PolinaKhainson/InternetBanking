package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListAllAccountsCommandTest {

	@Test
	public void test() {
		ListAllAccountsCommand listAllAccountsCommand = new ListAllAccountsCommand();
		assertNotNull(listAllAccountsCommand);
	}

}
