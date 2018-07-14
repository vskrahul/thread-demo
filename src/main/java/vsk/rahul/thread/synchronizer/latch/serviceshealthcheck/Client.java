/**
 * 
 */
package vsk.rahul.thread.synchronizer.latch.serviceshealthcheck;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class Client {

	private static final Logger logger = Logger.getLogger(Client.class);
	
	public static void main(String[] args) {
		try {
			if(ApplicationStartupCheck.checkExternalServices())
				logger.error("All servies are UP, we can go ahead.");
		} catch(Exception e) {
			logger.error("One of the service is down", e);
		}
	}
}