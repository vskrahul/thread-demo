/**
 * 
 */
package vsk.rahul.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class ReentrantLockDemo {

	public static void main(String[] args) throws Exception {
		ReentrantLock lock = new ReentrantLock();
		Thread t1 = new Thread(new Task(lock), "A");
		Thread t2 = new Thread(new Task(lock), "B");
		
		t1.start();
		t2.start();
		
		Thread.sleep(100);
	}

}

class Task implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Task.class);
	
	ReentrantLock lock = null;
	
	Task(ReentrantLock lock) {
		this.lock = lock;
	}
	
	@Override
	public void run() {
		if(Thread.currentThread().getName().equals("A")) {
			process();
		} else {
			process1();
		}
	}
	
	void process() {
		lock.lock();
		try {
			logger.info(Thread.currentThread().getName() + " is going to sleep.");
			Thread.sleep(10000);
			logger.info(Thread.currentThread().getName() + " is woke up.");
		} catch(InterruptedException e) {
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		lock.unlock();
	}
	
	void process1() {
		lock.lock();
		try {
			logger.info(Thread.currentThread().getName() + " is going to sleep.");
			Thread.sleep(10000);
			logger.info(Thread.currentThread().getName() + " is woke up.");
		} catch(InterruptedException e) {
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		lock.unlock();
	}
}