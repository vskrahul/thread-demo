/**
 * 
 */
package vsk.rahul.thread.interrupt;

import org.apache.log4j.Logger;

/**
 * If interrupt() is calling on thread instance :-
 * 
 * <ol>
 * 	<li> If thread is blocked - received {@link InterruptedException}
 * 	<li> If thread is running - interrupt flag set to true.
 * </ol>
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 11, 2018
 */
public class InterruptDemo {

	public static void main(String args[]) throws Exception {

		Runnable blockedThread = new InterruptTestWhenThreadIsBlocked();
		Runnable nonBlockedThread = new InterruptTestWhenThreadIsNotBlocked();		

		Thread t1 = new Thread(blockedThread, "Blocked Thread.");
		Thread t2 = new Thread(nonBlockedThread, "Non Blocked Thread.");

		t1.start();
		t2.start();

		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			throw e;
		}

		t1.interrupt();
		t2.interrupt();
	}
}

class InterruptTestWhenThreadIsBlocked implements Runnable {
	
	private static final Logger logger = Logger.getLogger(InterruptTestWhenThreadIsBlocked.class);

	public void run() {
		try {
			while(true) {
				Thread.sleep(100);
				if(Thread.currentThread().isInterrupted())
					break;
			}
		} catch(InterruptedException e) {
			logger.error(Thread.currentThread().getName() + " is interrupted while in sleeping"
				+ " state. Got error : " + e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
}

class InterruptTestWhenThreadIsNotBlocked implements Runnable {
	
	private static final Logger logger = Logger.getLogger(InterruptTestWhenThreadIsNotBlocked.class);

	public void run() {

		int interruptCount = 1;

		while(true) {
			if(Thread.currentThread().isInterrupted()) {
				logger.info(Thread.currentThread().getName() + " is interrupted - " + interruptCount);
				interruptCount++;
				break;
			}
		}
	}
}