package ua.nure.khainson.SummaryTask4.db.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Payment entity
 * 
 * @author P.Khainson
 * 
 */
public class Payment extends Entity {

	private static final long serialVersionUID = 4568900348319707921L;

	private long creditCardId;

	private long accountId;

	private int paymentStatusId;

	private BigDecimal sumOfMoney;

	private Timestamp dateTime;

	public long getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(long creditCardId) {
		this.creditCardId = creditCardId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public int getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(int paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public BigDecimal getSumOfMoney() {
		return sumOfMoney;
	}

	public void setSumOfMoney(BigDecimal sumOfMoney) {
		this.sumOfMoney = sumOfMoney;
	}

	public Timestamp getDateTime() {
		return (Timestamp) dateTime.clone();
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = (Timestamp) dateTime.clone();
	}

	@Override
	public String toString() {
		return "Payment [creditCardId=" + creditCardId + ", accountId="
				+ accountId + ", paymentStatusId=" + paymentStatusId
				+ ", sumOfMoney=" + sumOfMoney + ", dateTime=" + dateTime + "]";
	}

}
