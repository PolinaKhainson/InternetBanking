package ua.nure.khainson.SummaryTask4.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class LockStatusTest {

	@Test
	public void test() {
		LockStatus lockStatus = LockStatus.LOCKED;
		assertNotNull(lockStatus);
	}

}
