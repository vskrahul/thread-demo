/**
 * 
 */
package vsk.rahul.thread.synchronization;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class AccountDanger implements Runnable {
	
	private static final Logger logger = Logger.getLogger(AccountDanger.class);

	private Account account = new Account();
	
	@Override
	public void run() {
		
		for(int i = 0; i < 5; i++) {
			logger.info(Thread.currentThread().getName() + " withdraw number..." + (i+1));
			makeWithdrawal(10);
			if(account.getBalance() < 0)
				logger.info("Account is overdrawn!! " + Thread.currentThread().getName());
		}
	}
	
	private synchronized void makeWithdrawal(Integer amount) {
		if(account.getBalance() >= amount) {
			logger.info(Thread.currentThread().getName() + " is going to withdraw.");
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			account.makeWithdrawal(amount);
			logger.info(Thread.currentThread().getName() + " completed the withdrawal. " + account.getBalance());
		} else {
			logger.info("Not enough balance for " + Thread.currentThread().getName()
					+ " to withdraw, account balance is " + account.getBalance());
		}
	}
	
	public static void main(String[] args) {
		
		AccountDanger accnt = new AccountDanger();
		Thread rahul = new Thread(accnt, "Rahul");
		Thread rohit = new Thread(accnt, "Rohit");
		rahul.start();
		rohit.start();
	}
}
