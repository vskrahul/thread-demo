/**
 * 
 */
package vsk.rahul.thread.synchronizer.semaphore;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

/**
 * <p>
 * Sempahore POC using the example of Printers in an Organization.
 * 
 * <p>
 * This print queue represents all the available printers. 
 * Which are executing the print jobs in parallel but if no print is free then put block the all next job until the 
 * semaphore signal it.
 * 
 * @see {@link BlockingQueue}
 * @author Rahul Vishvakarma
 *
 * @created Jul 13, 2018
 */
public class PrinterQueue {
	
	private static final Logger logger = Logger.getLogger(PrinterQueue.class);

	private final transient Semaphore semaphore;
	
	private AtomicBoolean freePrinters[];
	
	public PrinterQueue(int printers) {
		this.semaphore = new Semaphore(printers);
		this.freePrinters = new AtomicBoolean[printers];
		
		for(int i = 0; i < freePrinters.length; i++) {
			freePrinters[i] = new AtomicBoolean();
			freePrinters[i].set(true);
		}
	}
	
	public void printJob(Object document) {
		try {
			/*
			 * Acquire the semaphore if any printer is free.
			 */
			semaphore.acquire();

			/*
			 * If printer is free lets get the index of that printer from freePrinters array which one has true.
			 */
			int assignedPrinter = getPrinter();
			
			Long duration = (long)5000;
            logger.info(Thread.currentThread().getName()
                    + ": Printer " + assignedPrinter
                    + " : Printing a Job during " + (duration / 1000)
                    + " seconds :: Time - " + new Date());
            Thread.sleep(duration);
            
            /*
             * print job is completed, update the printer free status in freePrinters array.
             */
            releasePrinter(assignedPrinter);
            
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			semaphore.release();
		}
	}
	
	private int getPrinter() {
		
		int number = -1;
		
		for(int i = 0; i < freePrinters.length; i++) {
			if(freePrinters[i].get()) {
				number = i;
				freePrinters[i].set(false);
				break;
			}
		}
		
		return number;
	}
	
	private void releasePrinter(int assignedPrinter) {
		freePrinters[assignedPrinter].set(true);
	}
	
}