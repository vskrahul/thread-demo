/**
 * 
 */
package vsk.rahul.thread.interrupt;

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
		} catch(InterruptedException e) {}

		t1.interrupt();
		t2.interrupt();
	}
}

class InterruptTestWhenThreadIsBlocked implements Runnable {

	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " is interrupted while in sleeping"
					+ " state. Got error : " + e.getMessage());
				break;
			}
		}
	}
}

class InterruptTestWhenThreadIsNotBlocked implements Runnable {

	public void run() {

		int interruptCount = 1;

		while(true) {
			if(Thread.currentThread().isInterrupted()) {
				System.out.println(Thread.currentThread().getName() + " is interrupted - " + interruptCount);
				interruptCount++;
				break;
			}
		}
	}
}