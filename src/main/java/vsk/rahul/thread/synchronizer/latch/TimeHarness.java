package vsk.rahul.thread.synchronizer.latch;

import java.util.concurrent.CountDownLatch;

public class TimeHarness {

	public static long timeTasks(int nThread, final Task task) throws InterruptedException {
		
		CountDownLatch startGate = new CountDownLatch(1);
		CountDownLatch endGate = new CountDownLatch(nThread);
		
		for(int i = 0; i < nThread; i++) {
			Thread t = new Thread(() -> {
				try {
					startGate.await(); // await current thread at start latch
					try {
						task.work();
					} finally {
						endGate.countDown();
					}
				} catch(InterruptedException e) {}
			});
			t.start();
		}
		long start = System.nanoTime();
		/*
		 * main thread will decrease the count down and all thread are 
		 * waiting at start gate will allow to continue.
		 */
		startGate.countDown();
		/*
		 * main thread is waiting at end gate.
		 * Until end gate opens.
		 */
		System.out.println("main thread is waiting.");
		endGate.await();
		
		System.out.println("end gate open for main thread.");
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(timeTasks(10, new Task()));
	}
}

class Task {
	
	public void work() {
		System.out.println(Thread.currentThread().getName() + " finished.");
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {}
	} 
	
}