/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * A runnable producer to keep the failed request in shared blocking queue.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Producer implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Producer.class);

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
				logger.info(Thread.currentThread().getName() + 
						String.format(" trying to put %s in queue.", request));
				queue.put(request);
				if(Thread.currentThread().isInterrupted()) 
					break;
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			// do logging here.
			logger.error(Thread.currentThread().getName() + " is interrupted.");
		}
	}
}