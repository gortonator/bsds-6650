package bsdsthreads;

/**
 *
 * @author igorton
 * Example to start three threads, sleep for short period while thraads run, and terminate
 */
public class SimpleThreadExample {

   
    public static void main(String[] args) {
      // create 3 threads and give them names
      NamingThread name0 = new NamingThread("thread0");
      NamingThread name1 = new NamingThread("thread1");
      NamingThread name2 = new NamingThread("thread2");
	
      //Create the threads
      Thread t0 = new Thread (name0);
      Thread t1 = new Thread (name1);
      Thread t2 = new Thread (name2);	
      
      // start the threads
      t0.start();
      t1.start();
      t2.start();
      
      //delay the main thread for a second
      try {
	Thread.currentThread().sleep(1000);
      } catch (InterruptedException e) {
      }

      //Display info about the main thread and terminate
      System.out.println(Thread.currentThread());
    }
}
