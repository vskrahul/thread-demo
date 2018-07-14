/**
 * 
 */
package vsk.rahul.thread.waitnotify;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class Receiver implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Receiver.class);

	private Data data;
	
	public Receiver(Data data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		
		String receivedMessage = data.receive();
		
		while(!receivedMessage.equals("End")) {
			
			receivedMessage = data.receive();
			
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error(e.getMessage(), e);
			}
		}
	}

}
