/**
 * 
 */
package vsk.rahul.thread.join;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 10, 2018
 */
public class JoinKeepTheLock {
	
	private static final Logger logger = Logger.getLogger(JoinKeepTheLock.class);

	public static void main(String[] args) throws InterruptedException {
		KeepLock r = new KeepLock();
		
		Thread t1 = new Thread(r, "A");
		Thread t2 = new Thread(r, "B");
		Thread t3 = new Thread(r, "C");
		
		t2.start();
		t3.start();
		t1.start();
		t1.join();
		Thread.yield();
		logger.info(Thread.currentThread().getName() + " All thread are dead.");
	}
}

class KeepLock implements Runnable {
	
	private static final Logger logger = Logger.getLogger(KeepLock.class);
	
	@Override
	public void run() {
		synchronized (this) {
			logger.info(Thread.currentThread().getName() + " is going to sleep.");
			
			try {
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			logger.info(Thread.currentThread().getName() + " woke up.");
		}
	}
}