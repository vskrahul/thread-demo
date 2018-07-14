/**
 * 
 */
package vsk.rahul.thread.synchronizer.latch.serviceshealthcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class ApplicationStartupCheck {

	private static List<HealthChecker> services;
	
	private static CountDownLatch latch;
	
	private ApplicationStartupCheck() {}
	
	private final static ApplicationStartupCheck instance = new ApplicationStartupCheck();
	
	public static final ApplicationStartupCheck instance() {
		return instance;
	}
	
	public static boolean checkExternalServices() throws Exception {
		latch = new CountDownLatch(2);
		
		services = new ArrayList<HealthChecker>();
		services.add(new DatabaseServer(latch));
		services.add(new ConfigServer(latch));
		
		Executor executor = Executors.newFixedThreadPool(services.size());
		
		for(final HealthChecker checker : services) {
			executor.execute(checker);
		}
		
		/*
		 * Calling thread is waiting all the service checker instances to come back with the status.
		 */
		latch.await();
		
		int howManyServicesAreNotUp = (int)services.stream().map(s -> s.isServiceUp()).filter(status -> !status).count();
		
		return howManyServicesAreNotUp == 0;
	}
}