package ua.nure.khainson.SummaryTask4.exeption;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.nure.khainson.SummaryTask4.exception.AppException;

public class AppExceptionTest {

	@Test
	public void test() {
		AppException appException = new AppException();
		assertNotNull(appException);
	}

}
