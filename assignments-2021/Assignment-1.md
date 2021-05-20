# CS6650 Summer 2021  Assignment 1

## Text Analysis Server - Building the Client

## Overview
We're going to build a high performance, scalable text analysis system over a series of three assignments

## Assignment 1: Building the Client
For the first assignment, you'll build a client that sends lines of text to a server for analysis. 
The server will simply accept and validate requests, and send an HTTP 200 response. 
In Assignment 2, we'll add the processing to the server. 
In Assignment 3, we'll get a little crazy, so make sure you lay solid code and design foundations in the first two assignments!

## Implement the Server API 

The initial server API is specified using [Swagger](https://app.swaggerhub.com/apis/gortonator/TextProcessor/1.0.2)

In this assignment you need to implement this API using a server technology of your choice. The course labs guide you through how to do this with Java servlets.
From SwaggerHub however, you can generate a server stub for application servers in Java, Python, Go, Scala from following menu option.

*Export-Server Stub*

So - you choose!! You'll ge best instructor/TA support for Java/servlets but we'll do our best to help whichever way you go.

The API implementation in the server for this assignment should simply:

1. Accept the parameters for the post operation as per the specification
1. Do basic parameter validation, and return a 4XX response code and error message if invalid values/formats supplied
1. If the request is valid, return a 200 response code and a response body with any valid integer

This should be a pretty simple task. Lab 2 shows how to implement a servlet and there's lots of information on servlet programming on the Interwebs. 
[Here's one example](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html). If you are not using servlets, Google is your friend!

Test the API locally with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools.

You then need to deploy the server on an AWS free tier instance you have created and configured in lab 1 and call the APIs successfully.

## Build the Client (Part 1)

This is the major part of this assignment. We want a multithreaded Java client that posts lines of text and exerts various loads on the server.

First you need to get a Java client to call your server APIs. You can generate a client API from the Swagger specification. Look at:

*Export-Client SDK-Java*

Unzip the client and follow the instructions in the README to incorporate the generated code in your client project.

The generated code contains thread-safe methods for calling the server APIs. 
Write a simple test that calls the API before proceeding, to establish that you have connectivity. 
The example in the README is your friend ;). You just need to look at the ApiClient methods to figure out how to point the example at your server. 

If you don't want to figure out the Swagger client, you can use the [Apache Java HTTP API](https://hc.apache.org/index.html).

Once you have your client calling the API, it's time to build the multithreaded client fun time!!

Your client should accept 2 arguments
1. an input file. [Use this one for testing](https://github.com/gortonator/bsds-6650/blob/master/assignments-2021/bsds-summer-2021-testdata.txt)
1. number of threads to run - maxThreads

Based on these values, your client will start up and 
1. process and validate the input parameters
1. read the input file one line at a time (ignore empty lines)
1. create maxThreads client threads that continuosly send a line from the file to the server

Basically you have to figure out how to distribute lines of the file to threads so that one line gets processed exactly once. 
You also need to figure out how to tell each thread there are no more lines to process so that each can shut down cleanly

For every request, the server will return an HTTP 200 response code for a successful POST operation. 
As soon as the 200 is received, each client thread should immediately get another line to send until the input has been exhausted.

If the client receives a 5XX response code (Web server error), or a 4XX response code (from your servlet) it should log the error to stderr.

Once all threads are started, your client needs to wait until all the threads successfully complete. 

When all threads are complete, the programs should print out:
1. total number of successful requests sent
1. total number of unsuccessful requests (ideally should be 0)
1. the total run time (wall time) for all phases to complete. Calculate this by taking a timestamp before any threads start sending requests and another after *all*  threads are complete.
1. throughput = requests per second = total number of requests/wall time

The client should calculate these and display them in the output window, and then cleanly terminate.

To reduce latencies, run your client as 'close' to the server as possible. Best is to run the client on a EC2 instance in the same data center as your server. Second best is to run the server in an AWS data center that is as near to your client as possible. eg if you are in Seattle running the client on your laptop, Oregon is probably closest.

To run on a remote client, you need to build an executable .jar file. [This can be done easily with maven](https://www.baeldung.com/executable-jar-with-maven)

## Building the Client (Part 2)
With your load generating client working wonderfully, we want to now instrument the client so we have deeper insights into the performance of the system. 
To this end, for each POST:
* before sending the request, take a timestamp
* when the HTTP response is received, take another timestamp
* calculate the latency (end - start) in milliseconds
* Write out a record containing {start time, request type (ie POST), latency, response code}. CSV is a good file format.

Once all phases have completed, we need to calculate:
* mean response time for POSTs (millisecs)
* median response time for POSTs (millisecs)
* the total wall time
* throughput = requests per second = total number of requests/wall time
* p99 (99th percentile) response time for POSTs [Here’s a nice article](https://www.elastic.co/blog/averages-can-dangerous-use-percentile) about why percentiles are important and why calculating them is not always
easy.
* max response time for POSTs 

You may want to do all the processing of latencies in your client after the test completes, or you may want to write a separate program to run after the test has completed that generates the results. Check your results against those calculated in a spreadsheet. 

The client should calculate these and display them in the output window in addition to the output from the previous step, and then cleanly terminate. 

You will want to compare your wall time/throughput with the result from Part 1. Ideally they should be 'close' (within 5% or so). If they are more than 10% slower, perhaps you have some client behavior to capture the statistics that is not as efficient as it should be. 

## Submission Requirements
Submit your work to Canvas Assignment 1 as a pdf document. The document should contain:

1. the URL for your git repo. *Make sure that the code for the client part 1 and part 2 are in seperate folders in your repo*
1. a 1-2 page description of your client design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works
1. Client (Part 1) - run your client with 32, 64, 128 and 256 threads (maxThreads). Include the outputs of each run in your submission (showing the wall time) and plot a simple chart showing the wall time by the number of threads. This should be a screen shot of your output window.
1. Client (Part 2) - run the client as per Part 1, showing the output window for each run. Also generate a plot of throughput and mean response time against number of threads. Again, this should be a screen shot of your output window.

## Bonus Points
You will get extra credit for either (or both) of the following:

### Break Things :)
Experiment with the number of threads your server can support and report when something on the server breaks. 

Perhaps with 4000 client threads your server will overload an IP buffer somewhere and refuse requests, or requests will take so long they will timeout and fail? 

Or depending on how you are capturing latencies, your client may start throwing OutOfMemory exceptions? This is probably a bug in your client, but still ;)

Stress testing is fun and instructive. It’s a skill you should be taking away from this course, so here’s a chance to practise! *Beware, the school network limits requests - run tests with more than 256 threads somewhere else!*

### Charting
It is usually interesting to plot average latencies over the whole duration of a test run. To do this you will have to capture  timestamps of when the request occurs, and then generate a plot that shows latencies against time (there’s a good example in the percentile article earlier). You might want to plot every request, or thinking ahead for assignment 2, put them in buckets of, for
example, a second interval, and plot the number of requests (throughput) or average response time for that time interval bucket.

## Grading:
1. Server implementation working (5 points)
1. Client design description (5 points) - clarity of description, good design practies used, accepts command line options/parameter file
1. Client Part 1 - (10 points) - 1 point per run output, 1 point for the chart, 5 points for sensible results!
1. Client Part 2 - (20 points) - 1 point per run, 1 point for the chart. 5 points for calculations of mean/median/p99/max/throughput (as long as they are sensible). 10 points for wall time within 5% of wall time for Client Step 1.
1. Bonus Points: Up to 4 bonus points for experiemnts and impressive outputs!!

# Deadline: 5/31, 11.59pm PST 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)

## Odds and Ends
### Problems with GSON import in .war filr in Intellij
Sometimes there's an issue building the GSON jar file into a servlet, such that when the servlet is deployed it fails because GSON is missing.

Try adding the gson jar to your project from the [Maven website](https://mvnrepository.com/artifact/com.google.code.gson/gson)

For IntelliJ you also need to:

1. Go to project structure
1. choose the artifacts tab
1. select the gson maven package and put it into the lib directory of the artifact. 

Compile and deploy ... and cross your fingers and toes!!

For more details [check this out](https://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project)

### Building Swagger Client with Java 11
You need to modify your POM, add the lines below:
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

