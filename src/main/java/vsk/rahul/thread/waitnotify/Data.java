/**
 * 
 */
package vsk.rahul.thread.waitnotify;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class Data {
	
	private static final Logger logger = Logger.getLogger(Data.class);

	private String packet;
	
	private volatile boolean transfer = true;
	
	public synchronized void send(String packet) {
		while(!transfer) {
			try {
				this.wait();
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		logger.info("Sender sending = " + packet);
		transfer = false;
		this.packet = packet;
		this.notifyAll();
	}
	
	public synchronized String receive() {
		while(transfer) {
			try {
				this.wait();
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		logger.info("Receiver received = " + packet);
		transfer = true;
		this.notifyAll();
		return packet;
	}
}