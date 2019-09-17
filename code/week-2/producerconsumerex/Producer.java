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
import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer drop) {
        this.buffer = drop;
    }

    public void run() {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            buffer.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {}
        }
        buffer.put("DONE");
    }
}
