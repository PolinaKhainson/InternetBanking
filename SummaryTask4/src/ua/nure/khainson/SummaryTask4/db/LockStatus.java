package ua.nure.khainson.SummaryTask4.db;

import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.User;

/**
 * LockStatus entity.
 * 
 * @author P.Khainson
 * 
 */
public enum LockStatus {
	LOCKED, UNLOCKED, WAITING_FOR_UNLOCK;

	public static LockStatus getUserLockStatus(User user) {
		int userLockStatusId = user.getLockStatusId();
		return LockStatus.values()[userLockStatusId];
	}

	public static LockStatus getAccountLockStatus(Account account) {
		int accountLockStatusId = account.getLockStatusId();
		return LockStatus.values()[accountLockStatusId];
	}

	public static LockStatus getByOrdinal(int ord) {
		for (LockStatus ls : LockStatus.values()) {
			if (ls.ordinal() == ord) {
				return ls;
			}
		}
		return null;
	}

	public String getName() {
		return name().toLowerCase().replace('_', ' ');
	}
}
