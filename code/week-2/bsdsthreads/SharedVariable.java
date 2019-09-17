package bsdsthreads;

import java.util.concurrent.CountDownLatch;


/**
 * @author Ian Gorton
 * Multiple threads increment a value concurrently. Method is synchronized so behaves correctly
 * Demonstrates us of a count down latch to implement barrier synchronization
 *
 */
public class SharedVariable {
	private static final int NUMTHREADS = 5000;
	private int val;
	private final CountDownLatch startSignal = new CountDownLatch(1);
	private final CountDownLatch endSignal = new CountDownLatch(NUMTHREADS);
	
	public synchronized void inc() {
		val++;
	}
	
	public int getVal() {
		return val;
	}
	
	public static void main(String[] args) throws InterruptedException {
	
            final SharedVariable rmw = new SharedVariable();

            for (int i = 0; i < NUMTHREADS; i++) {
                Runnable thread =  () -> { 
                  try {
                    // wait for the main thread to tell us to start  
                    rmw.startSignal.await();
                    rmw.inc();
                  } catch (InterruptedException e) { 
                  } finally {
                     // we've finished - let the main thread know 
                     rmw.endSignal.countDown();
                  }
                };
	        new Thread(thread).start();
               
            }

            rmw.startSignal.countDown();
            rmw.endSignal.await();
            System.out.println("Value should be " + NUMTHREADS + " - It is " + rmw.getVal());
    }
}
