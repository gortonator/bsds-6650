# CS6650 Spring 2023  Assignment 1

##### Overview

You are contracted by an insane billionnaire to investigate building a new Internet service taht implements a dating service that is totally gender-free and allows user to add short, up to 256 characters,  comments about potential matches. The service is to be known Twinder.

In this first phase you are asked to build a very simple prototype of the client and server. The insane billionaire is very cost conscious and requires a prototype that costs basically nothing to deploy and run. We'll build a client that generates and sends user responses to profiles to a server in the cloud. The server will simply accept and validate requests, and send an HTTP 200/201 response. In following phases, we'll add the processing and storage logic to the server, and send a richer set of requests. 

##### Task 1: Implement the Server API

The initial server API is specified using [Swagger](https://app.swaggerhub.com/apis/IGORTON/Twinder/1.0.0#)

In this assignment you need to implement the POST API for the /swipe endpoint using Java servlets. The API should:

1. Accept the parameters for the operations as per the specification
2. Do basic parameter validation, and return a 4XX response code and error message if invalid values/formats supplied
3. If the request is valid, return a 200/201 response code and some dummy data as a response body

Good simple illustrations of how to handle JSON request payloads are [here](https://edwin.baculsoft.com/2011/11/how-to-create-a-simple-servlet-to-handle-json-requests/) and [here](https://www.baeldung.com/servlet-json-response).

This should be a pretty simple task. Test the servlet API with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools. Deploy your server on (ideally) us-west2 on AWS Academy on an EC2 free tier instance you have created that runs tomcat.

## Build the Client (Part 1)

Build the Client (Part 1)

This is the major part of this assignment. We want a multithreaded Java client we can configure to upload a day of lift rides to the server and exert various loads on the server.

First you need to get a Java client to call your server APIs. You can generate a client API from the Swagger specification page. Look at:

*Export-Client SDK-Java*

Unzip the client and follow the instructions in the README to incorporate the generated code in your client project.

The generated code contains classes and methods for calling the server APIs.

Write a simple test that calls the API before proceeding, to establish that you have connectivity. The examples in the README and documentation are your friends ;).

To connect to your remote server on EC2 you need to call an ApiClient methods (hint - setBasePath(...)) . It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues. You then pass this ApiClient object to your SkiersApi object. Again, look at the constructors for SkiersApi. **(Note - you should never modify generated code - in this case you don't need to!)**

If you don't want to figure out the Swagger client, you can use the [Java 11 HTTP client classes](https://openjdk.org/groups/net/httpclient/intro.html) or the [Apache Java HTTP API](https://hc.apache.org/index.html).

Once you have your client calling the API, it's time to build the multithreaded client fun time!!

### Data Generation

Your client will send 200K POST requests to your server. You should create an object that generates a random skier lift ride event that can be used to form a POST request. Follow the rules below:

1. skierID - between 1 and 100000
2. resortID - between 1 and 10
3. liftID - between 1 and 40
4. seasonID - 2022
5. dayID - 1
6. time - between 1 and 360

### Multithreaded Client

This is the fun bit. Your aim is to upload all 200K lift ride events to your server *as quickly as possible*. To do this we'll use multiple threads. The only constraints on your design are as follows:

* At startup, you must create 32 threads that each send 1000 POST requests and terminate. Once any of these have completed you are free to create as few or as many threads as you like until all the 200K POSTS have been sent.

* Lift ride events must be generated in a single dedicated thread and be made available to the threads that make API calls. You need to design this mechanism so that:
  
  * a posting thread never has to wait for an event to be available. This would slow down your client
  
  * it consumes as little CPU and memory as possible, making maximum capacity available for making POST requests

The server will return an HTTP 201 response code for a successful POST operation. As soon as the 201 is received, the client thread should immediately send the next request until it has exhausted the number of requests to send.

### Handling Errors

If the client receives a 5XX response code (Web server error), or a 4XX response code (from your servlet), it should retry the request up to 5 times before counting it as a failed request. This probably means your server or network is down.

### On Completion

When all 200k requests have been successfully sent, all threads should terminate cleanly. The programs should finally print out:

1. number of successful requests sent
2. number of unsuccessful requests (should be 0)
3. the total run time (wall time) for all phases to complete. Calculate this by taking a timestamp before you startany threads and another after all threads are complete.
4. the total throughput **in requests per second** (total number of requests/wall time)

You should run the client on your laptop. Thus means each request will incur latency depending on where your server resides. By using us-west2, this latency will be pretty low from Seattle.

You should test how long a single request takes to estimate this latency. Run a simple test and send eg 10000 requests from a single thread to do this.

You can then calculate the expected throughput your client will see using Little's Law and use this to guide your design.

If your throughput is not close to this estimate for your test runs, you probably have a bug in your client.

## Building the Client (Part 2)

With your load generating client working wonderfully, we want to now instrument the client so we have deeper insights into the performance of the system.

To this end, for each POST request:

* before sending the POST, take a timestamp
* when the HTTP response is received, take another timestamp
* calculate the latency (end - start) in milliseconds
* Write out a record containing {start time, request type (ie POST), latency, response code}. CSV is a good file format.

Once all phases have completed, we need to calculate:

* mean response time (millisecs)
* median response time (millisecs)
* throughput = total number of requests/wall time (requests/second)
* p99 (99th percentile) response time. [Here’s a nice article](https://www.elastic.co/blog/averages-can-dangerous-use-percentile) about why percentiles are important and why calculating them is not always easy. (millisecs)
* min and max response time (millisecs)

You want to do all the processing of latencies in your client after the test completes. The client should calculate these and display them in the output window in addition to the output from the previous step, and then cleanly terminate.

## Bonus Points

A popular alternative to servlets for creating HTTP servers is Spring/SpringBoot. Spring has lots of very clever mechanisms for making it easy to write HTTP methods. As an experiment, deploy a Spring(boot) server on your EC2 instance that implements the POST method and run your fastest client configuration against this server. How does the throughput compare?

## Submission Requirements

Submit your work to Canvas Assignment 1 as a pdf document. The document should contain:

1. the URL for your git repo. *Make sure that the code for the client part 1 and part 2 are in seperate folders in your repo*
2. a 1-2 page description of your client design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works.Include Little's Law throughput predictions.
3. Client (Part 1) - This should be a screen shot of your output window with your wall time and throughput. Also make sure you include the client configuration in terms of number of threads used.
4. Client (Part 2) - run the client as per Part 1, showing the output window for each run with the specified performance statistics listed at the end.

## Grading:

1. Server implementation working (5 points)
2. Client design description (5 points) - clarity of description, good design practies used
3. Client Part 1 - (10 points) - Output window showing best throughput. Points deducted if actual throughput not close to Little's Law predictions.
4. Client Part 2 - (10 points) - 5 points for throughput within 5% of Client Part 1. 5 points for calculations of mean/median/p99/max/throughput (as long as they are sensible).
5. Bonus Points: (3 points) for comparison with Spring server
6. Bonus Points 2 points for fastest 3 clients, and 1 point for next fastest 3 clients.

## Additional Useful Information

### Building Swagger Client with Java 11

You need to modify your POM, add the following dependencies:

    <dependency>
        <groupId>javax.xml.bind</groupId>
        <artifactId>jaxb-api</artifactId>
        <version>2.2.11</version>
    </dependency>
    <dependency>
        <groupId>com.sun.xml.bind</groupId>
        <artifactId>jaxb-core</artifactId>
        <version>2.2.11</version>
    </dependency>
    <dependency>
        <groupId>com.sun.xml.bind</groupId>
        <artifactId>jaxb-impl</artifactId>
        <version>2.2.11</version>
    </dependency>
    <dependency>
        <groupId>javax.activation</groupId>
        <artifactId>activation</artifactId>
        <version>1.1.1</version>
    </dependency>
    
    <dependency>
        <groupId>javax.annotation</groupId>
        <artifactId>javax.annotation-api</artifactId>
        <version>1.3.2</version>
    </dependency>

### Problems with GSON import in .war file in Intellij

Sometimes there's an issue building the GSON jar file into a servlet, such that when the servlet is deployed it fails because GSON is missing.

Try adding the gson jar to your project from the [Maven website](https://mvnrepository.com/artifact/com.google.code.gson/gson)

For IntelliJ you also need to:

1. Go to project structure
2. choose the artifacts tab
3. select the gson maven package and put it into the lib directory of the artifact.

Compile and deploy ... and cross your fingers and toes!!

For more details [check this out](https://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project)

### Random number generation in Java Threads

Worth a read for [random number generation](https://plumbr.io/blog/locked-threads/shooting-yourself-in-the-foot-with-random-number-generators) in your client ;)

### Connection management with Apache HttpClient

Your client threads want to keep a connection open and send many requests. Check out this [stackoverflow post](https://stackoverflow.com/questions/30889984/whats-the-difference-between-closeablehttpresponse-close-and-httppost-release) to delve into the complexities of how to do it properly.




