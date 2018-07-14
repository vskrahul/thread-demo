/**
 * 
 */
package vsk.rahul.thread.synchronizer.semaphore;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class PrinterDemo {

	public static void main(String[] args) {
		PrinterQueue queue = new PrinterQueue(3);

		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(new PrinterJob(queue), "Thread " + i);
		}
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
	}
}
