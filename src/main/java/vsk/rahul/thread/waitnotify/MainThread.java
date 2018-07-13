/**
 * 
 */
package vsk.rahul.thread.waitnotify;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class MainThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ThreadA th = new ThreadA();
		
		Thread t = new Thread(th, "Thread-A");
		t.start();
		
		synchronized (th) {
			try {
				
				System.out.println(name() + " thread is waiting and released the monitor on 'th' "
						+ "until another thread having same monitor calls notify() or notifyAll()..."
						+ "\nIf no one will invoke notify()/notifyAll() then " + name() 
						+ " will be waiting forever :("
						+ "\n\nIf you don't believe try to comment notify() call in ThreadA run() method.\n");
				th.wait();
				System.out.println("\n\nThank God !! Someone called notify()/notifyAll() on 'th' instance while having "
						+ "the monitor of 'th' instance.");
			} catch(InterruptedException e) {}
		}
	}
	
	static String name() {
		return Thread.currentThread().getName();
	}

}

class ThreadA implements Runnable {
	int total;
	
	public void run() {
		synchronized (this) {
			for(int i = 0; i < 100; i++) {
				total += i;
			}
			System.out.println(Thread.currentThread().getName() + " completed Total : " + this.total);
			notify();
		}
	}
}