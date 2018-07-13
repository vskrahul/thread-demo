/**
 * 
 */
package vsk.rahul.thread.join;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 10, 2018
 */
public class JoinKeepTheLock {

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
		System.out.println(Thread.currentThread().getName() + " All thread are dead.");
	}
}

class KeepLock implements Runnable {
	@Override
	public void run() {
		synchronized (this) {
			System.out.println(Thread.currentThread().getName() + " is going to sleep.");
			
			try {
				Thread.sleep(3000);
			} catch(InterruptedException e) {}
			
			System.out.println(Thread.currentThread().getName() + " woke up.");
		}
	}
}