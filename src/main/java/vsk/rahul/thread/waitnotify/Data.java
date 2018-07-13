/**
 * 
 */
package vsk.rahul.thread.waitnotify;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class Data {

	private String packet;
	
	private volatile boolean transfer = true;
	
	public synchronized void send(String packet) {
		while(!transfer) {
			try {
				this.wait();
			} catch(InterruptedException e) {}
		}
		System.out.println("Sender sending = " + packet);
		transfer = false;
		this.packet = packet;
		this.notify();
	}
	
	public synchronized String receive() {
		while(transfer) {
			try {
				this.wait();
			} catch(InterruptedException e) {}
		}
		System.out.println("Receiver received = " + packet);
		transfer = true;
		this.notify();
		return packet;
	}
}
