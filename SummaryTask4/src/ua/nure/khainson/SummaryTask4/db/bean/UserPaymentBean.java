package ua.nure.khainson.SummaryTask4.db.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import ua.nure.khainson.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * 
 * <pre>
 * |p.id|c.credit_card_number|a.account_number|a.lock_status_id| ps.name| p.sum_of_money| p.payment_date_time|
 * </pre>
 * 
 * @author P.Khainson
 * 
 */

public class UserPaymentBean extends Entity {

	public UserPaymentBean() {
		super();
	}

	private static final long serialVersionUID = -2912848507926373511L;

	private long paymentId;

	private int creditCardNumber;

	private int accountNumber;

	private int accountStatus;

	private BigDecimal sumOfMoney;

	private String paymentStatusName;

	private Date dateOfPayment;

	private Time timeOfPayment;

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public int getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getSumOfMoney() {
		return sumOfMoney;
	}

	public void setSumOfMoney(BigDecimal sumOfMoney) {
		this.sumOfMoney = sumOfMoney;
	}

	public String getPaymentStatusName() {
		return paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public Date getDateOfPayment() {
		return (Date) dateOfPayment.clone();
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = (Date) dateOfPayment.clone();
	}

	public Time getTimeOfPayment() {
		return timeOfPayment;
	}

	public void setTimeOfPayment(Time timeOfPayment) {
		this.timeOfPayment = timeOfPayment;
	}

	@Override
	public String toString() {
		return "UserPaymentBean [paymentId=" + paymentId
				+ ", creditCardNumber=" + creditCardNumber + ", accountNumber="
				+ accountNumber + ", accountStatus=" + accountStatus
				+ ", sumOfMoney=" + sumOfMoney + ", paymentStatusName="
				+ paymentStatusName + ", dateOfPayment=" + dateOfPayment
				+ ", timeOfPayment=" + timeOfPayment + "]";
	}

}
