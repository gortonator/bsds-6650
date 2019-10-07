package logicalclock;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ian Gortan
 */
public class LamportClient implements Runnable {
    
    private MessageBuffer buffer;
    final private int NUM_EVENTS = 10; 
    private int pid;
    

    public LamportClient(MessageBuffer buf, int pid) {
        this.buffer = buf;
        this.pid = pid;
    }

    public void run() {
        Random random = new Random();
        //start at -1 just so we can index in to events array easily
        int clock = -1;
        ArrayList<String> events  = new ArrayList<String> ();         
        
        // create a message object
        Message m = new Message (pid, clock, "init");
        m.setPid(pid);
        m.setClock(clock) ;
        
        // add some 'reandomness' to the order at startup
        if ( (pid%2) == 0 ) {
            clock++;
            m.setClock(clock);
            String event = "[INTERNAL] "  + "PID="  + Integer.toString(pid) + " CLOCK:" + Integer.toString(clock); 
            events.add(event);
        }
        for (int i = 0; i < NUM_EVENTS ; i++ ) {
            // let's generate an event 
            clock++;
            m.setClock(clock);
            if ( ((i%2) == 0) ) {
                // internal event
                String event = "[INTERNAL] "  + "PID="  + Integer.toString(pid) + " CLOCK:" + Integer.toString(clock); 
                events.add(event);
            } else {
                // external event
                String event = "[SEND]     " + "PID="  + Integer.toString(pid) + " CLOCK:" + Integer.toString(clock); 
                events.add(event);
                m.setMessageID(event);
                buffer.put(m); // sleep for a random time to induce different orders
                try {
                      Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {}
            
                // get a message and set clock according to Lamport algorithm
                //System.out.println("Getting a message: " + Integer.toString(pid));
                Message recMsg = buffer.get(pid);
                 // chck it wasn't a message we sent (null returned) as we want to ignore those
                 
                if (recMsg != null) {
                    // add the event ...
                    if (recMsg.getClock() > clock) {
                        clock = recMsg.getClock() + 1; 
                    } else {
                        clock++;
                    }
                    event = "[RECEIVE]  " + "FROM PID="  + Integer.toString(recMsg.getPid()) + " CLOCK IN:" + Integer.toString(recMsg.getClock()) + " LOCAL CLOCK " + Integer.toString(clock); 
                    events.add(event);
                }
                
                
            }
           
                
        }
        
        System.out.println("Process " + pid + " complete. Here's my view of the order of events");
		
        // TO DO add your code here to output results
		
        
            
          
     }
 
}
    

