package ua.nure.khainson.SummaryTask4.db.bean;

import java.util.List;

import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.Entity;

/**
 * Provide virtual entity:
 * 
 * Credit card with all accounts, assigned to it as well.
 * 
 * @author P.Khainson
 * 
 */
public class CreditCardAccountsBean extends Entity {

	public CreditCardAccountsBean() {
		super();
	}

	private static final long serialVersionUID = 4290380405232731496L;

	private CreditCard creditCard;

	private List<Account> accounts;

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "CreditCardAccountsBean [creditCard=" + creditCard
				+ ", accounts=" + accounts + "]";
	}

}
