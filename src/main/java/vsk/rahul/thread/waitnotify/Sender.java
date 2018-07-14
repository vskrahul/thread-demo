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
public class Sender implements Runnable {
	
	private static final Logger logger = Logger.getLogger(Sender.class);

	private Data data;
	
	public Sender(Data data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		String packets[] = {
			"First packet",
			"Second packet",
			"Third packet",
			"Fourth packet",
			"End"
		};
		
		for(String packet : packets) {
			data.send(packet);
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error(e.getMessage(), e);
			}
		}
	}
}
