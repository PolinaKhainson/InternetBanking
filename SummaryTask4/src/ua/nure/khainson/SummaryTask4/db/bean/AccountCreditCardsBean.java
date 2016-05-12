package ua.nure.khainson.SummaryTask4.db.bean;

import java.util.List;
import java.util.Map;

import ua.nure.khainson.SummaryTask4.db.entity.Account;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCard;
import ua.nure.khainson.SummaryTask4.db.entity.Entity;

/**
 * Provide virtual entity:
 * 
 * Account with name of its owner and all credit cards, assigned to it as well.
 * 
 * @author P.Khainson
 * 
 */
public class AccountCreditCardsBean extends Entity {

	public AccountCreditCardsBean() {
		super();
	}

	private static final long serialVersionUID = -7587650697555210188L;

	private Account account;

	private String ownerFirstName;

	private String ownerLastName;

	private List<CreditCard> creditCardslist;
	
	private Map<Long, String> creditCards;

	public Map<Long, String> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Map<Long, String> creditCards) {
		this.creditCards = creditCards;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public List<CreditCard> getCreditCardslist() {
		return creditCardslist;
	}

	public void setCreditCardslist(List<CreditCard> creditCardslist) {
		this.creditCardslist = creditCardslist;
	}

	@Override
	public String toString() {
		return "AccountCreditCardsBean [account=" + account
				+ ", ownerFirstName=" + ownerFirstName + ", ownerLastName="
				+ ownerLastName + ", creditCardslist=" + creditCardslist + "]";
	}

}
