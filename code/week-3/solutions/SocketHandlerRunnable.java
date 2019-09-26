package socketexamples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/*
* Simple thread to handle a socket request
* Author: Ian Gorton
*/
 public class SocketHandlerRunnable implements Runnable {
    private final Socket conn;
    private boolean running = true;
     
    private final ActiveCount threadCount;

  SocketHandlerRunnable(Socket s, ActiveCount threads) {
    conn = s;
    threadCount = threads;
  }

    @Override
    public void run() {
        threadCount.incrementCount();
        System.out.println("Accepted Client: Address - "
            + conn.getInetAddress().getHostName());
        try {
          BufferedReader   in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          PrintWriter   out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));

          String clientID = in.readLine();
          // Uncomment to see what client sent
          //System.out.println(clientID);
          out.println("Active Server Thread Count = " + Integer.toString( threadCount.getCount() ));
          out.flush();    
          // Uncomment to ensure reply set
          //System.out.println("Reply sent");

        } catch (IOException e) {
               threadCount.decrementCount();
        } finally { 
            threadCount.decrementCount();
             try { conn.close(); } catch (IOException e) {}
            System.out.println("Thread exiting");
        }
  }
    
}
