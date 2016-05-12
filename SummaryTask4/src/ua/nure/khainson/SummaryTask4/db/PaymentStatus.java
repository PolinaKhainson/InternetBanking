package ua.nure.khainson.SummaryTask4.db;

import ua.nure.khainson.SummaryTask4.db.entity.Payment;

/**
 * PaymentStatus entity.
 * 
 * @author P.Khainson
 * 
 */
public enum PaymentStatus {
	PREPARED, CONFIRMED;

	public static PaymentStatus getPaymentStatus(Payment payment) {
		int paymentStatusId = payment.getPaymentStatusId();
		return PaymentStatus.values()[paymentStatusId];
	}

	public String getName() {
		return name().toLowerCase();
	}
}
