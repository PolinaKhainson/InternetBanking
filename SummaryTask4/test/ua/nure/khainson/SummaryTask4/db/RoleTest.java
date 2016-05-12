package ua.nure.khainson.SummaryTask4.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoleTest {

	@Test
	public void test() {
		Role role = Role.ADMIN;
		assertNotNull(role);
	}

}
