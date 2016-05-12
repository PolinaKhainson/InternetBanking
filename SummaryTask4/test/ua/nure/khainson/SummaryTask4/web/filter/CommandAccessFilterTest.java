package ua.nure.khainson.SummaryTask4.web.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandAccessFilterTest {

	@Test
	public void test() {
		CommandAccessFilter commandAccessFilter  = new CommandAccessFilter();
		assertNotNull(commandAccessFilter);
	}

}
