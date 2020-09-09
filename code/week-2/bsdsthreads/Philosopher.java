/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsdsthreads;

/**
 *
 * @author igortn
 */
public class Philosopher implements Runnable {

    private final Object leftChopStick;
    private final Object rightChopStick;

    Philosopher(Object leftChopStick, Object rightChopStick) {
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
    }
    private void LogEvent(String event) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + event);
        Thread.sleep(1000);
    }

   public void run() {
        try {
            while (true) {
                LogEvent(": Thinking deeply"); 
                synchronized (leftChopStick) {
                    LogEvent(": Picked up left chop stick");
                    synchronized (rightChopStick) {
                        LogEvent( ": Picked up right chopstick  - eating"); // eating
                        LogEvent( ": Put down right chopstick");
                    }
                    LogEvent(": Put down left chopstick. Returning to deep thinking");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
