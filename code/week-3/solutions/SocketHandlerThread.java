package socketexamples;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/*
* Simple thread to handle a single socket request
* Author: Ian Gorton
*/
class SocketHandlerThread extends Thread {
  private final Socket conn;
  private boolean running = true;
  private final ActiveCount threadCount;

  SocketHandlerThread(Socket s, ActiveCount threads) {
    conn = s;
    threadCount = threads;
  }

  public void run() {
    threadCount.incrementCount();
    System.out.println("Accepted Client: Address - "
        + conn.getInetAddress().getHostName());
    try {
      BufferedReader   in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      PrintWriter   out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
      
      String clientID = in.readLine();
      // Uncomment to see what client sent
      // System.out.println(clientID);
      out.println("Active Server Thread Count = " + Integer.toString( threadCount.getCount() ));
      out.flush();  
      // uncomment to ensure reply sent
      // System.out.println("Reply sent");
      
    } catch (IOException e) {
        System.out.println("Exception in thread");
    } finally {
        try { conn.close(); } catch (IOException e) {}
        threadCount.decrementCount();
        System.out.println("Thread exiting");
    }
  }
  
} //end class
