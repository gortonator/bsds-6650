package socketexamples;

/**
 * Skeleton socket client. 
 * Accepts host/port on command line or defaults to localhost/12031
 * Then (should) starts MAX_Threads and waits for them all to terminate before terminating main()
 * @author Ian Gorton
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreaded {
    
    static CyclicBarrier barrier; 
    
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException  {
        String hostName;
        final int MAX_THREADS = 50 ;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in SocketServer
        }
        // TO DO finalize the initialization of barrier below
        barrier = new CyclicBarrier(MAX_THREADS+1);
        
        // TO DO create and start MAX_THREADS SocketClientThread
        for (int i=0; i< MAX_THREADS; i++){
             new SocketClientThread(hostName, port, barrier).start();
        }
        
        // TO DO wait for all threads to complete
        barrier.await();
        
        System.out.println("Terminating ....");
                
    }

      
}
