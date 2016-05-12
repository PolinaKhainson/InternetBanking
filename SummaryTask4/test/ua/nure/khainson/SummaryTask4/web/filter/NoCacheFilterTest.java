package ua.nure.khainson.SummaryTask4.web.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoCacheFilterTest {

	@Test
	public void test() {
		NoCacheFilter noCacheFilter = new NoCacheFilter();
		assertNotNull(noCacheFilter);
	}

}
