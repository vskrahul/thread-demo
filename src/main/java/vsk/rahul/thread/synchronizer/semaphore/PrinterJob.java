/**
 * 
 */
package vsk.rahul.thread.synchronizer.semaphore;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class PrinterJob implements Runnable {

	private PrinterQueue queue;
	
	public PrinterJob(PrinterQueue queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		System.err.printf("%s: Going to print a document\n", Thread
                .currentThread().getName());
		queue.printJob(new Object());
	}

}
