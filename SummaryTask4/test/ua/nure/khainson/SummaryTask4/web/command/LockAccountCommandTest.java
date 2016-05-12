package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class LockAccountCommandTest {

	@Test
	public void test() {
		LockAccountCommand lockAccountCommand = new LockAccountCommand();
		assertNotNull(lockAccountCommand);
	}

}
