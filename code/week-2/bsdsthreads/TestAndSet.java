package bsdsthreads;


/**
 * 
 * @author Ian Gorton
 * This code is thread safe - the check on the shared value is synchronized. 
 *
 */
public class TestAndSet {
	private int val = 0;
        final static private int NUMTHREADS = 10;
	
	public synchronized void testVal() {
	  if (val == 0) {
	    System.out.println(Thread.currentThread().getName() + " - Value Set");
            val = 1;
	  }
	  else {
	    System.out.println(Thread.currentThread().getName() + " - Value not modified");
	  }
	}

	public static void main(String[] args) {
	  final TestAndSet tSet = new TestAndSet();
		
	  for (int i = 0; i < NUMTHREADS; i++) {
            new Thread(new Runnable() {
	    @Override
	      public void run() {
	        tSet.testVal();
              }
            }, "Thread" + i).start();
	  }
	}
}
