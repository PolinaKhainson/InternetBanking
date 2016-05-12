package ua.nure.khainson.SummaryTask4.db.entity;

import java.math.BigDecimal;

/**
 * Account entity.
 * 
 * @author P.Khainson
 * 
 */
public class Account extends Entity {

	private static final long serialVersionUID = -4460273598240364346L;

	private long userId;

	private int accountNumber;

	private int lockStatusId;

	private BigDecimal sumOnAccount;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getLockStatusId() {
		return lockStatusId;
	}

	public void setLockStatusId(int lockStatusId) {
		this.lockStatusId = lockStatusId;
	}

	public BigDecimal getSumOnAccount() {
		return sumOnAccount;
	}

	public void setSumOnAccount(BigDecimal sumOnAccount) {
		this.sumOnAccount = sumOnAccount;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", accountNumber=" + accountNumber
				+ ", lockStatusId=" + lockStatusId + ", sumOnAccount="
				+ sumOnAccount + "]";
	}

}
