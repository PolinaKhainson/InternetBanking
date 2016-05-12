package ua.nure.khainson.SummaryTask4.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentStatusTest {

	@Test
	public void test() {
		PaymentStatus paymentStatus = PaymentStatus.PREPARED;
		assertNotNull(paymentStatus);
	}

}
