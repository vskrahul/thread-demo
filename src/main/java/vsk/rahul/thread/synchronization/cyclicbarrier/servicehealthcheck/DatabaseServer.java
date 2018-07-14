/**
 * 
 */
package vsk.rahul.thread.synchronization.cyclicbarrier.servicehealthcheck;

import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class DatabaseServer extends HealthChecker {
	
	private static final Logger logger = Logger.getLogger(DatabaseServer.class);

	public DatabaseServer(CyclicBarrier latch) {
		super(latch, "Database Server");
	}
	
	@Override
	public void verifyService() {
		try {
			Thread.sleep(1000);
			logger.info(super.getServiceName() + " is up");
		} catch(InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(super.getServiceName() + " is not up", e);
			throw e;
		}
	}
}