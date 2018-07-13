/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.util.concurrent.BlockingQueue;

/**
 * A runnable producer to keep the failed request in shared blocking queue.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Producer implements Runnable {

	private BlockingQueue<Request> queue;
	
	private BlockingQueue<Request> failedRequestQueue;
	
	public Producer(BlockingQueue<Request> queue, BlockingQueue<Request> failedRequestQueue) {
		this.queue = queue;
		this.failedRequestQueue = failedRequestQueue;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Request request = failedRequestQueue.take();
				System.out.println(Thread.currentThread().getName() + 
						String.format(" trying to put %s in queue.", request));
				queue.put(request);
			}
		} catch(InterruptedException e) {
			// do logging here.
			System.out.println(Thread.currentThread().getName() + " is interrupted.");
		}
	}
}