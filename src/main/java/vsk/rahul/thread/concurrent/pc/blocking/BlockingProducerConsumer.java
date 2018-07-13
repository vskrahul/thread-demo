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

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 11, 2018
 */
public class BlockingProducerConsumer {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting....");
		BlockingQueue<Request> queue = new LinkedBlockingQueue<>(10);
		BlockingQueue<Request> failedRequestQueue = new LinkedBlockingQueue<>();
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		Dao dao = new Dao();
		List<Request> requests = new ArrayList<>();
		boolean flag = false;
		for(int i = 0; i < 100; i++) {
			Request req = new Request(i + 101, new Integer(i + 101).toString(), flag = !flag);
			requests.add(req);
		}
		
		/*
		 * Have started 5 consumer.
		 */
		for(int i = 0; i < 2; i++)
			pool.submit(new Producer(queue, failedRequestQueue));
		
		/*
		 * Have started 5 consumer.
		 */
		for(int i = 0; i < 2; i++)
			pool.submit(new Consumer(queue, failedRequestQueue, dao));
		
		
		/*
		 * Process each request in separate thread.
		 */
		for(Request req : requests) {
			Runnable run = new RequestThread(dao, req, failedRequestQueue);
			pool.submit(run);
		}
		Thread.sleep(12000);
		failedRequestQueue.put(new Request(1111, "Dummy".toString(), false));
	}
}

class RequestThread implements Runnable {
	
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
			failedRequestQueue.offer(req);
		}
	}
}