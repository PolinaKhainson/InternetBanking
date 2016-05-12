package ua.nure.khainson.SummaryTask4.db;

import ua.nure.khainson.SummaryTask4.db.entity.User;


/**
 * Role entity.
 * 
 * @author P.Khainson
 * 
 */

public enum Role {
	ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}

