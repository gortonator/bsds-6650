package bsdsthreads;

/**
 *
 * @author igortn
 * creates N threads and waits for them to execute using join()
 */
public class JoinThreadsExample {
    
    private final static int NUMTHREADS = 5;
    public static void main(String[] args) {
      // create runnables and give them names
      NamingThread runnables[] = new NamingThread [NUMTHREADS];
      
      for (int i=0; i < NUMTHREADS ; i++ ) {
          String name = "thread" + i;
          runnables[i] = new NamingThread(name);
      }
      	
      //Create and start the threads
      Thread tids[] = new Thread[NUMTHREADS]; 
      for (int i=0; i < NUMTHREADS ; i++ ) {
         tids[i] = new Thread (runnables[i]);
         tids[i].start(); 
      }

      // wait for each one to finish
       try {
	 for (int i=0; i < NUMTHREADS ; i++ ) {
           tids[i].join();
         }
       } catch (InterruptedException e) {
       }
       
      //Display info about the main thread and terminate
      System.out.println(Thread.currentThread());
    }
    
}
