/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.net.ConnectException;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Consumer implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Consumer.class);

	private BlockingQueue<Request> queue;
	
	private BlockingQueue<Request> failedRequestQueue;
	
	private Dao dao;
	
	public Consumer(BlockingQueue<Request> queue, BlockingQueue<Request> failedRequestQueue, Dao dao) {
		super();
		this.queue = queue;
		this.dao = dao;
		this.failedRequestQueue = failedRequestQueue;
	}

	@Override
	public void run()  {
		Request request = null;
		try {
			while(true) {
				request = queue.take();
				request.setConnectionTimeout(true);
				logger.info(String.format("%s trying to process %s again.", Thread.currentThread().getName(), request));
				process(request);
				if(Thread.currentThread().isInterrupted()) 
					break;
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error(Thread.currentThread().getName() + " is interrupted.");
		} 
	}
	
	private void process(Request request) {
		try {
			dao.process(request);
		} catch(ConnectException e) {
			if(failedRequestQueue.offer(request))
				try {
					failedRequestQueue.put(request);
				} catch(InterruptedException ex) {
					logger.error(e.getMessage(), ex);
					Thread.currentThread().interrupt();
				}
		}
	}
}