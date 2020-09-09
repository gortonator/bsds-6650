package bsdsthreads;


/**
 * 
 * @author Ian Gorton
 * Simple test to show race condition when multiple threads update a shared variable
 *
 */
public class RequestCounterBad {
  final static private int NUMTHREADS = 100000;
  private int count = 0;
	
  public  void inc() {
    count++;
  }
	
  public int getVal() {
    return this.count;
  }
	
  public static void main(String[] args) throws InterruptedException {
    final RequestCounterBad counter = new RequestCounterBad();
		
    for (int i = 0; i < NUMTHREADS; i++) {
        // lambda runnable creation - interface only has a single method so lambda works fine 
        Runnable thread =  () -> { counter.inc(); };
	new Thread(thread).start();
    }
		
    Thread.sleep(5000);
    System.out.println("Value should be equal to " + NUMTHREADS + " It is: " + counter.getVal());
  }
}
