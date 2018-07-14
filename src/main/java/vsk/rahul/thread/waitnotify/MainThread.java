/**
 * 
 */
package vsk.rahul.thread.waitnotify;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class MainThread {
	
	private static final Logger logger = Logger.getLogger(MainThread.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		ThreadA th = new ThreadA();
		
		Thread t = new Thread(th, "Thread-A");
		t.start();
		
		synchronized (th) {
			try {
				
				logger.info(name() + " thread is waiting and released the monitor on 'th' "
						+ "until another thread having same monitor calls notify() or notifyAll()..."
						+ "\nIf no one will invoke notify()/notifyAll() then " + name() 
						+ " will be waiting forever :("
						+ "\n\nIf you don't believe try to comment notify() call in ThreadA run() method.\n");
				th.wait();
				logger.info("\n\nThank God !! Someone called notify()/notifyAll() on 'th' instance while having "
						+ "the monitor of 'th' instance.");
			} catch(InterruptedException e) {
				throw e;
			}
		}
	}
	
	static String name() {
		return Thread.currentThread().getName();
	}

}

class ThreadA implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ThreadA.class);
	
	int total;
	
	public void run() {
		synchronized (this) {
			for(int i = 0; i < 100; i++) {
				total += i;
			}
			logger.info(Thread.currentThread().getName() + " completed Total : " + this.total);
			this.notifyAll();
		}
	}
}