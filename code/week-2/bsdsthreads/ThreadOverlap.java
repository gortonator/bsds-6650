package bsdsthreads;

/**
 * 
 * @author igorton
 * Shows how threads interleave during execution
 */
public class ThreadOverlap {
	
	public static void print() {
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + " - iteration: " + i);
		}
	}

	public static void main(String[] args) {
				
		Runnable test = new Runnable() {
			@Override
			public void run() {
				print();
			}
		};
		
		new Thread(test, "Man City are great").start();
		new Thread(test, "Man Utd Losers").start();
                new Thread(test, "Liverpool are too good right now").start();
	}
}
