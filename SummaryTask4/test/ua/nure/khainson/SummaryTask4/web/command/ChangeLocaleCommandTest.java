package ua.nure.khainson.SummaryTask4.web.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChangeLocaleCommandTest {

	@Test
	public void test() {
		ChangeLocaleCommand changeLocalCommand = new ChangeLocaleCommand();
		assertNotNull(changeLocalCommand);
	}

}
