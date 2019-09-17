package bsdsthreads;


/**
 * 
 * @author Ian Gorton
 * Simple test to show race condition when multiple threads update a shared variable
 *
 */
public class SharedVariableBroken {
  final static private int NUMTHREADS = 10000;
  private int val = 0;
	
  public  void inc() {
    val++;
  }
	
  public int getVal() {
    return this.val;
  }
	
  public static void main(String[] args) throws InterruptedException {
    final SharedVariableBroken rmw = new SharedVariableBroken();
		
    for (int i = 0; i < NUMTHREADS; i++) {
        // lambda runnable creation - interface only has a single method so lambda works fine 
        Runnable thread =  () -> { rmw.inc(); };
	new Thread(thread).start();
    }
		
    Thread.sleep(5000);
    System.out.println("Value should be equal to " + NUMTHREADS + " It is: " + rmw.getVal());
  }
}
