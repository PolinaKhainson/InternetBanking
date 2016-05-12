package ua.nure.khainson.SummaryTask4.exeption;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.nure.khainson.SummaryTask4.exception.DBException;

public class DBExceptionTest {

	@Test
	public void test() {
		DBException dBException = new DBException();
		assertNotNull(dBException);
	}

}
