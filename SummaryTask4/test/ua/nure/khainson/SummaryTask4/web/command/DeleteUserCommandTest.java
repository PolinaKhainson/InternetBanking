package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeleteUserCommandTest {

	@Test
	public void test() {
		DeleteUserCommand deleteUserCommand = new DeleteUserCommand();
		assertNotNull(deleteUserCommand);
	}

}
