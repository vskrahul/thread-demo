/**
 * 
 */
package vsk.rahul.thread.synchronizer.latch.serviceshealthcheck;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class ConfigServer extends HealthChecker {
	
	private static final Logger logger = Logger.getLogger(ConfigServer.class);

	public ConfigServer(CountDownLatch latch) {
		super(latch, "Config Server");
	}
	
	@Override
	public void verifyService() {
		try {
			Thread.sleep(1000);
			logger.info(super.getServiceName() + " is up");
		} catch(InterruptedException e) {
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		} catch(Exception e) {
			logger.error(super.getServiceName() + " is not up");
			throw e;
		}
	}

}
