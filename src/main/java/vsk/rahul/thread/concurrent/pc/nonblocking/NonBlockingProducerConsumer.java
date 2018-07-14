/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.nonblocking;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

/**
 * Non-blocking producer and consumer implementation.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 11, 2018
 */
public class NonBlockingProducerConsumer {
	
	public static void main(String[] args) {
		ConcurrentLinkedQueue<String> unboundedQueue = new ConcurrentLinkedQueue<>();
		
		Thread producer = new Thread(new Producer(unboundedQueue));
		Thread consumer = new Thread(new Producer(unboundedQueue));
		
		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable {

	private ConcurrentLinkedQueue<String> unboundedQueue;
	
	Producer(ConcurrentLinkedQueue<String> unboundedQueue) {
		this.unboundedQueue = unboundedQueue;
	}
	
	@Override
	public void run() {
		try {
			unboundedQueue.offer("");
			Thread.sleep(100);
		} catch(Exception e) {}
	}
}

class Consumer implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Consumer.class);
	
	private ConcurrentLinkedQueue<String> unboundedQueue;
	
	Consumer(ConcurrentLinkedQueue<String> unboundedQueue) {
		this.unboundedQueue = unboundedQueue;
	}
	
	@Override
	public void run() {
		String str = null;
		try {
			str = unboundedQueue.peek();
			System.err.println(str);
			Thread.sleep(100);
		} catch(InterruptedException e) {
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
	}
}