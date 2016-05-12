package ua.nure.khainson.SummaryTask4.db.entity;


import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class AccountTest extends Assert {
	private Account account = null;
	@Before
	public void setup() {
		account = new Account();
		account.setId(1L);
		account.setLockStatusId(1);
		account.setAccountNumber(1);
		account.setUserId(1);
		account.setSumOnAccount(new BigDecimal("1"));
	}
	@Test
	public void testGettersAndSetters() {
		assertNotNull(account);
		assertEquals(1, account.getId().longValue());
		assertEquals(1, account.getLockStatusId());
		assertEquals(1, account.getAccountNumber());
		assertEquals(1, account.getUserId());
		assertEquals(new BigDecimal("1"), account.getSumOnAccount());
	}
	@Test
	public void testToString() {
		String expected = "Account [userId=1, accountNumber=1, lockStatusId=1, sumOnAccount=1]";
		String actual = account.toString();
		assertNotNull(actual);
		assertEquals(expected, actual);
		
	}

}
