/**
 * 
 */
package vsk.rahul.thread.concurrent.pc.blocking;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 11, 2018
 */
public class BlockingProducerConsumer {
	
	private static final Logger logger = Logger.getLogger(BlockingProducerConsumer.class);

	public static void main(String[] args) throws Exception {
		logger.info("Starting....");
		BlockingQueue<Request> queue = new LinkedBlockingQueue<>(10);
		BlockingQueue<Request> failedRequestQueue = new LinkedBlockingQueue<>();
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		Dao dao = new Dao();
		List<Request> requests = new ArrayList<>();
		boolean flag = false;
		for(int i = 0; i < 100; i++) {
			flag = !flag;
			Request req = new Request(i + 101, Integer.toString(i + 101), flag);
			requests.add(req);
		}
		
		/*
		 * Have started 5 consumer.
		 */
		for(int i = 0; i < 1; i++)
			pool.submit(new Producer(queue, failedRequestQueue));
		
		/*
		 * Have started 5 consumer.
		 */
		for(int i = 0; i < 1; i++)
			pool.submit(new Consumer(queue, failedRequestQueue, dao));
		
		
		/*
		 * Process each request in separate thread.
		 */
		for(Request req : requests) {
			Runnable run = new RequestThread(dao, req, failedRequestQueue);
			pool.submit(run);
		}
		Thread.sleep(12000);
		failedRequestQueue.put(new Request(1111, "Dummy-1", false));
		Thread.sleep(12000);
		failedRequestQueue.put(new Request(1111, "Dummy-2", false));
		Thread.sleep(12000);
		failedRequestQueue.put(new Request(1111, "Dummy-3", false));
		Thread.sleep(12000);
		failedRequestQueue.put(new Request(1111, "Dummy-4", false));
	}
}

class RequestThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(RequestThread.class);
	
	private Dao dao;
	
	private Request req;
	
	BlockingQueue<Request> failedRequestQueue;
	
	public RequestThread(Dao dao, Request req, BlockingQueue<Request> failedRequestQueue) {
		this.dao = dao;
		this.req = req;
		this.failedRequestQueue = failedRequestQueue;
	}

	@Override
	public void run() {
		try {
			dao.process(req);
		} catch(ConnectException e) {
			/*
			 * If failed to process create producer to process put the request in queue.
			 */
			if(failedRequestQueue.offer(req))
				try {
					failedRequestQueue.put(req);
				} catch(InterruptedException ex) {
					logger.error(e.getMessage(), ex);
					Thread.currentThread().interrupt();
				}
		}
	}
}