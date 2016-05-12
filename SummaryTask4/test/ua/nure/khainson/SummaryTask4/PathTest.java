package ua.nure.khainson.SummaryTask4;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathTest {

	@Test
	public void test() {
		String path = Path.ADMIN_PAGES_PATH;
		assertNotNull(path);
	}

}
