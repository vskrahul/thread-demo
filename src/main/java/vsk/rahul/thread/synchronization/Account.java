/**
 * 
 */
package vsk.rahul.thread.synchronization;

/**
 * Representing account with some balance and method to check balance and make withdraw.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class Account {

	private Integer balance = 50;
	
	public Integer getBalance() {
		return this.balance;
	}
	
	public void makeWithdrawal(Integer amount) {
		this.balance = this.balance - amount;
	}
}
