package ua.nure.khainson.SummaryTask4.db.entity;

import java.sql.Date;

/**
 * CreditCard entity.
 * 
 * @author P.Khainson
 * 
 */
public class CreditCard extends Entity {

	private static final long serialVersionUID = -3596933939641130870L;

	private long userId;

	private int cardNumber;

	private Date endingDate;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getEndingDate() {
		return (Date) endingDate.clone();
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = (Date) endingDate.clone();
	}

	@Override
	public String toString() {
		return "CreditCard [userId=" + userId + ", cardNumber=" + cardNumber
				+ ", endingDate=" + endingDate + "]";
	}

}
