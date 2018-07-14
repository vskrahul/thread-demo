/**
 * 
 */
package vsk.rahul.thread.join;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

/**
 * join() example.
 * 
 * @author Rahul Vishvakarma
 *
 * @created Jul 9, 2018
 */
public class JoinExample {
	
	private static final Logger logger = Logger.getLogger(JoinExample.class);

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new MyRunnable(), "t1");
        Thread t2 = new Thread(new MyRunnable(), "t2");
        Thread t3 = new Thread(new MyRunnable(), "t3");
        
        t1.start();
        
        //start second thread after waiting for 2 seconds or if it's dead
        try {
        	// main thread will wait for max 2 seconds or until the t1 is finished.
            t1.join(2000);
        } catch (InterruptedException e) {
        	logger.error(e.getMessage(), e);
            throw e;
        }
        
        t2.start();
        
        //start third thread only when first thread is dead
        try {
            t1.join();
        } catch (InterruptedException e) {
        	logger.error(e.getMessage(), e);
            throw e;
        }
        
        t3.start();
        
        //let all threads finish execution before finishing main thread
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
        	logger.error(e.getMessage(), e);
            throw e;
        }
        
       logger.info("All threads are dead, exiting main thread " + currentTimestamp());
	}
	
	public static String currentTimestamp() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    	return formatter.format(LocalDateTime.now());
    }

}

class MyRunnable implements Runnable{
	
	private static final Logger logger = Logger.getLogger(MyRunnable.class);

    @Override
    public void run() {
        logger.info("Thread started:::"+Thread.currentThread().getName() + currentTimestamp());
        try {
        	// sleep at least for 4 seconds
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        	logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        logger.info("Thread ended:::"+Thread.currentThread().getName() + currentTimestamp());
    }
    
    public String currentTimestamp() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    	return " " + formatter.format(LocalDateTime.now());
    }
    
}