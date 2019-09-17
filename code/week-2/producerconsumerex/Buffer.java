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
public class Buffer {
    // Message buffer between producer to consumer.
    private String message;
    // True if consumer must wait for producer to send message,
    // false if producer must wait for consumer to retrieve message.
    private boolean empty = true;

    public synchronized String retrieve() {
        // Wait until message is available.
        while (empty) {
            try {
                System.out.println("Waiting for a message");
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = true;
        // Notify producer that buffer is empty
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        // Wait until message has been retrieved.
        while (!empty) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        empty = false;
        // Store message.
        this.message = message;
        // Notify consumer that message is available
        notifyAll();
    }
}
