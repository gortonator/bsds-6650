package socketexamples;

/**
 *
 * @author Ian Gorton
 * Basic socket server that implements a thread-per-connection model:
 * 1) starts and listens for connections on port 12031
 * 2) When a connection received, spawn a thread to handle connection
 * 
 */



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class SocketServer {
  public static void main(String[] args) throws Exception {
    // create socket listener  
    ServerSocket m_ServerSocket = new ServerSocket(12031);
    // create object o count active threads
    ActiveCount threadCount = new ActiveCount();
    System.out.println("Server started .....");
    while (true) {
      // acept connection and start thread  
      Socket clientSocket = m_ServerSocket.accept();
      SocketHandlerThread server = new SocketHandlerThread (clientSocket, threadCount);
      server.start();
     
    }
  }
}

