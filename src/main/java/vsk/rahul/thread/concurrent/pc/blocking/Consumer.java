/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.net.ConnectException;
import java.util.concurrent.BlockingQueue;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 12, 2018
 */
public class Consumer implements Runnable {

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
				System.out.println(String.format("%s trying to process %s again.", Thread.currentThread().getName(), request));
				dao.process(request);
			}
		} catch(InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + " is interrupted.");
		} catch(ConnectException e) {
			try {
				failedRequestQueue.offer(request);
			} catch(Exception ee) {}
		}
	}
}