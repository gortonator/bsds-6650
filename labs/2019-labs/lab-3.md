# CS6650 Lab 3  

## Simple Performance Analysis Experiment
### Aims: 
1. Experiment with a simple load test with a servlet
1. Build a multithreaded servlet client using the Apache HttpClient library
1. Understand Little's Law in the context of a client-server system

### Deploy a Simple Servlet
Using your newly acquired skills from labs 1 and 2, we're going to deploy a universal salutations servlet as the basis for our experiment. 
~~~

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
 
   private String msg;

   public void init() throws ServletException {
      // Initialization
      msg = "Hello World";
   }
   
   // handle a GET request
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
      // Set response content type to text
      response.setContentType("text/html");
	  
      // sleep for 1000ms. You can vary this value for different tests
	  Thread.sleep(1000);

      // Send the response
      PrintWriter out = response.getWriter();
      out.println("<h1>" + msg + "</h1>");
   }

   public void destroy() {
      // nothing to do here
   }
}
~~~

Build, deploy to tomcat, test with Postman. You know the gig ;)

Note that this servlet will take _at least_ 1 second to respond. This delay is inserted to simulate the processing delay in a real application.

### Build a HTTP Client
We now need to build a Java client that will call our server. We'll use the Apache HttpClient library. There's an excellent tutorial [here](https://hc.apache.org/httpclient-3.x/tutorial.html). 

It shouldn't take more than a few minutes to work through the tutorial and the code example at the end provides all you need for your client. Steal selectively :)

Test the client against your server. When it connects and get a valid response. move on to the next section.

### Make the client multithreaded
We want to create a client that exerts a load on our server using multiple threads. There's an example from the lecture in Week 2 that provides a template for this - RequestCountBarrier.java.

You can find the code [here](https://github.com/gortonator/bsds-6650/tree/master/code/week-2/bsdsthreads).

Your task is make the servlet client from the previous step into a thread which is started by your main() in a loop. Initially create a 100 threads, with each thread issuing one call to the servlet. 

Before the threads are created take a timestamp using 
~~~
System.currentTimeMillis();
~~~
Do likewise when all threads have completed and print out the time taken to execute the program before terminating.

Remember every request will take ~1 second to execute. How long does your client take to excute 100 requests? Any idea why?

### Tomcat Configuration
Tomcat is a highly configurable beast. You can modify the behavior of Tomcat by changing the parameter values in the server.xml file in the tomcat installation.

We're interested in the number of threads Tomcat makes available to service requests. By default it is 200. 

Let's modify this to be, say, 10. ie
~~~
<Connector port="8080" protocol="HTTP/1.1"
	       maxThreads="10"
~~~

Stop and restart Tomcat and run your client again. Is the execution time different?

Experiment with the number of threads in the server, client and sleep delay in the servlet. As you change the values can you see any pattern in the resulting execution times?

### Little's Law
Little’s law is universal formula for any system where a queue is present. From a bank to a distributed system. It states:

"The long-term average number of customers in a stable system N is equal to the long-term average effective arrival rate, λ, multiplied by the average time a customer spends in the system, W; or expressed algebraically: N = λW."

* Arrival Rate: The rate at which the customers are entering the system is known as arrival rate.
* Exit Rate: The rate at which the customers are leaving the system is known as exit rate.

Little’s Law assumes a stable system so the arrival rate and exit rate are identical.

We can use Little’s Law in distributed systems to relate the total number of users/requests, server throughput & the average request latency.

Throughput is number of requests processed per unit time; It can be used as the exit rate = arrival rate (λ).

Response time – average response time is amount of time a request spends in the System (W). 

This gives us:

N = Throughput * Response Time

Can you use Little's Law to make sense of the client execution times you are seeing in your tests in this lab as your vary teh number of client and server threads, and the servlet sleep time?

### Additional Issues to Explore - Tomcat Performance Monitoring

Server technolgies like Tomcat and MySQL provide sophisticated tools to monitor and measure server performance. [This article](https://www.datadoghq.com/blog/tomcat-monitoring-tools/) walks you through various Tomcat monitoring capabilities. 

Understanding how to use the built-in Tomcat monitoring tools and JConsole will probably be very helpful in the assignments in this course ;)


[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
