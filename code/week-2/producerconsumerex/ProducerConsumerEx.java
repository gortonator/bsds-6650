/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package producerconsumerex;

/**
 *
 * @author igorton
 */
public class ProducerConsumerEx {

    public static void main(String[] args) throws InterruptedException{
        Buffer drop = new Buffer(); 
        (new Thread(new Consumer(drop))).start();
        (new Thread(new Consumer(drop))).start();
        Thread.sleep(5000);
        (new Thread(new Producer(drop))).start();
        (new Thread(new Producer(drop))).start();
        
    }
}
