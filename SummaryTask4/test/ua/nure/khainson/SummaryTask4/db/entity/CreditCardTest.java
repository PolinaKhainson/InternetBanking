package ua.nure.khainson.SummaryTask4.db.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class CreditCardTest {
	private  CreditCard creditCard = null;
	private  Date date = new Date(1);
	@Before
	public void setup() {
		creditCard = new CreditCard();
		creditCard.setId(1L);
		creditCard.setUserId(1);
		creditCard.setCardNumber(1);
		creditCard.setEndingDate(date);
	}
	@Test
	public void testGettersAndSetters() {
		assertNotNull(creditCard);
		assertEquals(1, creditCard.getId().longValue());
		assertEquals(1, creditCard.getUserId());
		assertEquals(1, creditCard.getCardNumber());
		assertEquals(new Date(1), creditCard.getEndingDate());
	}
	@Test
	public void testToString() {
		System.out.println(creditCard);
		String expected = "CreditCard [userId=1, cardNumber=1, endingDate=1970-01-01]";
		String actual = creditCard.toString();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
	}
}
