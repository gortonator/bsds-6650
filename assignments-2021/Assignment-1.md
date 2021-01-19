# CS6650 Spring 2021  Assignment 1

## Scaling the Giant Tigle

##Overview
A old decrepit supermarket chain has just had a huge injection of cash from a mystery multi-billionaire and is embarking on a rapid acquisition strategy to buy up new stores nationwide. 
These new stores need to be integrated into their existing business systems. They hire your development business to help them undertake this massive exercise.

## Assignment 1
For the first part of your contract, you'll build a client that generates and sends synthetic item purchases to a server in the cloud. The server will simply accept and validate requests, and send an HTTP 200/201 response. 
In Assignment 2, we'll add the processing and storage logic to the server, and send a richer set of requests. In Assignments 3 and 4, we'll get a little crazy, so make sure you lay solid code and design foundations in the first two assignments!

## Implement the Server API 

The initial server API is specified using [Swagger](https://app.swaggerhub.com/apis/gortonator/GianTigle/1.0.0#)

In this assignment you need to implement this API using a Java servlets. The API should simply:

1. Accept the parameters for each operations as per the specification
1. Do basic parameter validation, and return a 4XX response code and error message if invalid values/formats supplied
1. If the request is valid, return a 200/201 response code and some dummy valid data as a response body

This should be a pretty simple task. Lab 2 shows how to implement a servlet and there's lots of information on servlet programming on the Interwebs. [Here's one example](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html)

Test the servlet API with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools.

Make sure you can load the resulting .war file onto your EC2 free tier instance you have created and configured in lab 1 and call the APIs successfully.

## Build the Client (Part 1)

This is the major part of this assignment. We want a multithreaded Java client that we can configure to upload a day of item purchases from multiple stores and exert various loads on the server.

First you need to get a Java client to call your server APIs. You can generate a client API from the Swagger specification. Look at:

*Export-Client SDK-Java*

The generated code contains thread-safe methods for calling the server APIs. Write a simple test that calls the API before proceeding, to establish that you have connectivity.

If you don't want to figure out the Swagger client, you can use the [Apache Java HTTP API](https://hc.apache.org/index.html).

Once you have your client calling the API, it's time to build the client fun time!!

Stores operate for 9 hours per day, and are in 3 different time zones, meaning that the opening of stores is staggered. This needs to be reflected in our client.

Your client should accept a set of parameters from the command line (or a properties file) at startup. These are:
1. maximum number of stores to simulate (maxStores)
1. number of customers/store  (default 1000). This is the range of custIDs per store
1. maximum itemID - default 100000
1. number of purchases per hour: (default 60) - thus is numPurchases
1. number of items for each purchase  (range 1-20, default 5)
1. date - default to 20210101
1. IP/port address of the server 


Based on these values, your client will start up and 
1. process and validate the input parameters
1. Create a thread for every store (1..maxStores). Each store thread will, for every hour they are open, send *numPurchases* POST requests to the server. Each POST will have the default number of items to purchase in the request body.
1. Each request needs to randomly select a custID and itemIDs for the order. For custIDs, generate a value between (storeIDx1000) and (storeIDx1000)+number of customers/store. 
1. In the request body, generate a value between 1..5 for the number of items purchased.

We also need our client to simulate the staggered opening times of stores across timezones. We need to do this as follows:

Phase 1, the *east* phase, will launch (maxStores/4) threads.
After any store thread has sent 3 hour of purchases, launch another (maxStores/4) threads - the *central* phase.
After any store thread has sent 5 hours of purchases, launch the remaining (maxStores/2) threads - the *west* phase.

For example if numStores=64, we will launch 16 threads initially, another 16 threads after 3 hours, and 32 threads after 5 hours of purchases at any stores. 

For every request, the server will return an HTTP 200/201 response code for a successful GET/POST operation. As soon as the 200/201 is received, the client should immediately send the next request until it has exhausted the number of requests to send.

If the client receives a 5XX response code (Web server error), or a 4XX response code (from your servlet) it should log the error to stderr.

Once all threads are started, your client needs to wait until all the threads successfully complete. 

When all threads from all phases are complete, the programs should print out:
1. total number of successful requests sent
1. total number of unsuccessful requests (ideally should be 0)
1. the total run time (wall time) for all phases to complete. Calculate this by taking a timestamp before commencing Phase 1 and another after *all* Phase 3 threads are complete.
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
1. Client (Part 1) - run your client with 32, 64, 128 and 256 threads (maxStores) , and all other values set to default. Include the outputs of each run in your submission (showing the wall time) and plot a simple chart showing the wall time by the number of threads. This should be a screen shot of your output window.
1. Client (Part 2) - run the client as per Part 1, showing the output window for each run. Also generate a plot of throughput and mean response time against number of threads. Again, this should be a screen shot of your output window.

## Bonus Points
You will get extra credit for either (or both) of the following:

### Break Things :)
Experiment with the number of threads your server can support and report when something breaks. Perhaps with 1000 threads your server will overload an IP buffer somewhere, or requests will take so long they will timeout and fail? Or depending on how you are capturing latencies, your client may start throwing OutOfMemory exceptions? Stress testing is fun and instructive. It’s a skill you should be taking away from this course, so here’s a chance to practise! *Beware, the school network limits requests - run tests with more than 256 threads somewhere else!*

### Charting
It is usually interesting to plot average latencies over the whole duration of a test run. To do this you will have to capture  timestamps of when the request occurs, and then generate a plot that shows latencies against time (there’s a good example in the percentile article earlier). You might want to plot every request, or thinking ahead for assignment 2, put them in buckets of, for
example, a second interval, and plot the average response time for that time interval bucket.

## Grading:
1. Server implementation working (5 points)
1. Client design description (5 points) - clarity of description, good design practies used, accepts command line options/parameter file
1. Client Part 1 - (10 points) - 1 point per run output, 1 point for the chart, 5 points for sensible results!
1. Client Part 2 - (20 points) - 1 point per run, 1 point for the chart. 5 points for calculations of mean/median/p99/max/throughput (as long as they are sensible). 10 points for wall time within 5% of wall time for Client Step 1.
1. Bonus Points: Up to 5 bonus points for experiemnts and impressive outputs!!

# Deadline: 2/10, 11.59pm PST 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)


