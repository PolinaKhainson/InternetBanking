package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandTest {

	@Test
	public void test() {
		Command command = new LoginCommand();
		assertNotNull(command);
		
	}

}
