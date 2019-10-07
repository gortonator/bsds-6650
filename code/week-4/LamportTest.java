/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalclock;

/**
 *
 * @author Ian Gortan
 */
public class LamportTest {
    
    public static void main(String[] args) {
        System.out.println("starting ....");
        MessageBuffer buffer = new MessageBuffer(10);
        (new Thread(new LamportClient(buffer, 0))).start();
        (new Thread(new LamportClient(buffer, 1))).start();
        (new Thread(new LamportClient(buffer, 2))).start();
        
        
     
      }
}
    

