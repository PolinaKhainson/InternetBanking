package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class LockUserCommandTest {

	@Test
	public void test() {
		LockUserCommand lockUserCommand = new LockUserCommand();
		assertNotNull(lockUserCommand);
	}

}
