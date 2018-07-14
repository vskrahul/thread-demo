/**
 * 
 */
package vsk.rahul.thread.synchronization.cyclicbarrier.servicehealthcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class ApplicationStartupCheck {
	
	private static List<HealthChecker> services;
	
	private static CyclicBarrier cyclicBarrier;
	
	private ApplicationStartupCheck() {}
	
	private final static ApplicationStartupCheck instance = new ApplicationStartupCheck();
	
	public static final ApplicationStartupCheck instance() {
		return instance;
	}
	
	public static boolean checkExternalServices() throws Exception {
		cyclicBarrier = new CyclicBarrier(3);
		
		services = new ArrayList<HealthChecker>();
		services.add(new DatabaseServer(cyclicBarrier));
		services.add(new ConfigServer(cyclicBarrier));
		
		Executor executor = Executors.newFixedThreadPool(services.size());
		
		for(final HealthChecker checker : services) {
			executor.execute(checker);
		}
		
		/*
		 * Calling thread is waiting for all the service checker 
		 * instances to come to reach to cyclic barrier once they have been completed
		 * the verifyService() call.
		 */
		cyclicBarrier.await();
		
		int howManyServicesAreNotUp = (int)services.stream().map(s -> s.isServiceUp()).filter(status -> !status).count();
		
		return howManyServicesAreNotUp == 0;
	}
}