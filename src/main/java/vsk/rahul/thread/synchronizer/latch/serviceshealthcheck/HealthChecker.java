/**
 * 
 */
package vsk.rahul.thread.synchronizer.latch.serviceshealthcheck;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * Health checker base class.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public abstract class HealthChecker implements Runnable {
	
	private static final Logger logger = Logger.getLogger(HealthChecker.class);

	/**
	 * Should be equal to the number of services we want check before doing something else.
	 */
	private CountDownLatch latch;
	
	private String serviceName;
	
	private Boolean serviceUp;
	
	public HealthChecker(CountDownLatch _latch, String _serviceName) {
		this.latch = _latch;
		this.serviceName = _serviceName;
	}

	@Override
	public void run() {
		try {
			verifyService();
			serviceUp = true;
		} catch(Throwable e) {
			logger.error(e.getMessage());
			serviceUp = false;
		} finally {
			if(latch != null)
				latch.countDown();
		}
	}
	
	public abstract void verifyService() throws Throwable;
	
	public String getServiceName() {
        return serviceName;
    }
 
    public boolean isServiceUp() {
        return serviceUp;
    }
}