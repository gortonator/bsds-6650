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
public class DiningPhilosophers {
 private final static int NUMPHILOSOPHERS=5; 
 private final static int NUMCHOPSTICKS = 5 ; 
 public static void main(String[] args) throws Exception {
        
        final Philosopher[] ph = new Philosopher[NUMPHILOSOPHERS];
        Object[] chopSticks = new Object[NUMCHOPSTICKS];
 
        for (int i = 0; i < NUMCHOPSTICKS; i++) {
            chopSticks[i] = new Object();
        }
 
        for (int i = 0; i < NUMPHILOSOPHERS; i++) {
            Object leftChopStick = chopSticks[i];
            Object rightChopStick = chopSticks[(i + 1) % chopSticks.length];
            if (i == NUMPHILOSOPHERS - 1) {
                 
                // The last philosopher picks up the right fork first
                ph[i] = new Philosopher(rightChopStick, leftChopStick); 
            } else {
                ph[i] = new Philosopher(leftChopStick, rightChopStick);
            }
          
            Thread th
              = new Thread(ph[i], "Philosopher " + (i + 1));
            th.start();
        }
    }
}
