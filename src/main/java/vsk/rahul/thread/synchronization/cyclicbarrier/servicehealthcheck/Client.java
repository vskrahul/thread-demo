/**
 * 
 */
package vsk.rahul.thread.synchronization.cyclicbarrier.servicehealthcheck;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 14, 2018
 */
public class Client {
	
	private static final Logger logger = Logger.getLogger(Client.class);

	public static void main(String[] args) throws Exception {
		try {
			if (ApplicationStartupCheck.checkExternalServices())
				logger.error("All servies are UP, we can go ahead.");
		} catch (Exception e) {
			logger.error("One of the service is down", e);
			throw e;
		}
	}

}
