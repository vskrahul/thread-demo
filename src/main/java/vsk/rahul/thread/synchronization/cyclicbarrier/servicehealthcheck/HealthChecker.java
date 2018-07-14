/**
 * 
 */
package vsk.rahul.thread.synchronization.cyclicbarrier.servicehealthcheck;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
	private CyclicBarrier cyclicBarrier;
	
	private String serviceName;
	
	private Boolean serviceUp;
	
	public HealthChecker(CyclicBarrier cyclicBarrier, String serviceName) {
		this.cyclicBarrier = cyclicBarrier;
		this.serviceName = serviceName;
	}

	@Override
	public void run() {
		try {
			verifyService();
			serviceUp = true;
			cyclicBarrier.await();
		} catch(BrokenBarrierException e) {
			logger.error("Cyclic barrier is broken", e);
		} catch(InterruptedException e) {
			logger.error(e.getMessage(), e);
			Thread.currentThread().interrupt();
		} catch(Throwable e) {
			serviceUp = false;
			logger.error("Something is wrong.", e);
		}
	}
	
	public abstract void verifyService() throws Exception;
	
	public String getServiceName() {
        return serviceName;
    }
 
    public boolean isServiceUp() {
        return serviceUp;
    }
}