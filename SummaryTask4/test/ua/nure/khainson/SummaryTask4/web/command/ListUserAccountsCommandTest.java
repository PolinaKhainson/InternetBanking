package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListUserAccountsCommandTest {

	@Test
	public void test() {
		ListUserAccountsCommand listUserAccountsCommand = new ListUserAccountsCommand();
		assertNotNull(listUserAccountsCommand);
	}

}
