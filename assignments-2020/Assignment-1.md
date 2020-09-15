# CS6650 Fall 2020  Assignment 1

## Overview

You work for Ikkyone - a global acquirer of ski resorts that is homogenezing skiing around the world. Ikkyone ski resorts all use RFID lift ticket readers so that every time a skier gets on a ski lift, the time of the ride and the skier ID are recorded.

In this course, through a series of assignments, we'll build a scalable distributed cloud-based system that can record all lift rides from all Ikkyone resorts. This data can then be used as a basis for data analysis, answering such questions as:
* which lifts are most heavily used?
* which skiers ride the most lifts?
* How many lifts do skiers ride on average per day at resort X?

In Assignment 1, we'll build a client that generates and sends lift ride data to a server in the cloud. The server will simply accept and validate requests, and send an HTTP 200/201 response. In Assignment 2, we'll add the processing and storage logic to the server, and send a richer set of requests. In Assignments 3 and 4, we'll get a little crazy, so make sure you lay solid code and design foundations in the first two assignments!

## Implement the Server API 

The initial server API is specified using [Swagger](hhttps://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/1.11#free)

In this assignment you need to implement this API using a Java servlets. Each API should:

1. Accept the parameters for each operations as per the specification
1. Do basic parameter validation, and return a 4XX response code and error message if invalid values/formats supplied
1. If the request is valid, return a 200/201 response code and some dummy valid data as a response body

This should be a pretty simple task. Lab 2 shows how to implement a servlet and there's lots of information on servlet programming on the Interwebs. [Here's one example](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaServlets.html)

Test each servlet API with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools.

Make sure you can load the resulting .war file onto your EC2 free tier instance you have created and configured in lab 1 and call the APIs successfully.

## Build the Client (Part 1)

This is the major part of this assignment. We want a multithreaded Java client that we can configure to upload a day of lift rides to the server and exert various loads on the server.

Your client should accept a set of parameters from the command line (or a parameter file) at startup. These are:
1. maximum number of threads to run (maxThreads - max 256)
1. number of skier to generate lift rides for  (numSkiers - default 50000), This is effectively the skier's ID (skierID)
1. number of ski lifts  (numLifts - range 5-60, default 40)
1. the ski day number - default to 1
1. the resort name which is the resortID - default to "SilverMt"
1. IP/port address of the server 

In addition, each ski day is of length 420 minutes (7 hours - 9am-4pm) from when the lifts open until they all close.

Based on these values, your client will start up and execute 3 phases, with each phase sending a large number of lift ride events to the server API.

Phase 1, the *startup* phase, will launch (maxThreads/4) threads, and each thread will be passed:
* a start and end range for skierIDs, so that each thread has an identical number of skierIDs, caluculated as numSkiers/(maxThreads/4). Pass each thread a disjoint range of skierIDs so that the whole range of IDs is covered by the threads, ie, thread 0 has skierIDs from 1 to (numSkiers/(maxThreads/4)), thread 1 has skierIDs from (1x(numSkiers/(maxThreads/4)+1) to (numSkiers/(maxThreads/4))x2, etc
* a start and end time, for this phase this is the first 90 minutes of the ski day (1-90)

For example if numThreads=64 and numSkiers=1024, we will launch 16 threads, with thread 0 passed skierID range 1 to 64, thread 1 range 65 to 128, and so on.

Once each thread has started it should send *100* POST requests to the server to store a new lift ride, followed by *5* GET requests, as follows. 

Each POST should *randomly* select:
1. a skierID from the range of ids passed to the thread
1. a lift number (liftID)
1. a time from the range of minutes passed to each thread (start and end time - same for each thread)

Each GET randomly selects a skierID and calls /skiers/{resortID}/days/{dayID}/skiers/{skierID}

The server will return an HTTP 200/201 response code for a successful GET/POST operation. As soon as the 200/201 is received, the client should immediately send the next request until it has exhausted the number of requests to send.

If the client receives a 5XX response code (Web server error), or a 4XX response code (from your servlet) it should log the error using [log4j](https://logging.apache.org/log4j/2.x/) and move to the next request.

Once 10% (rounded up) of the threads in Phase 1 have completed, Phase 2, the *peak phase* should begin. Phase 2 behaves like Phase 1, except:
* it creates *numThreads* threads
* the start and end time interval is 91 to 360
* each thread is passed a disjoint skierID range of size (numSkiers/numThreads)

As above, each thread will sen 100 POSTs and 5 GET requests.

Finally, once 10% of the threads in Phase 2 complete, Phase 3 should begin. Phase 3, the *cooldown phase*, is identical to Phase 1 except:
1. it sends 10 GET requests per thread instead of 5
1. the time range 16 361 to 420.

When all threads from all phases are complete, the programs should print out:
1. number of successful requests sent
1. number of unsuccessful requests (ideally should be 0)
1. the total run time (wall time) for all phases to complete. Calculate this by taking a timestamp before commencing Phase 1 and another after *all* Phase 3 threads are complete.
1. throughput = requests per second = total number of requests/wall time

The client should calculate these and display them in the output window, and then cleanly terminate.

To reduce latencies, run your client as 'close' to the server as possible. Best is to run teh client on a EC2 instance in the same data center as your server. Second best is to run the server in an AWS data center that is as near to your client as possible. eg if you are in Seattle running the client on your laptop, Oregon is probably closest.

## Building the Client (Part 2)
With your load generating client working wonderfully, we want to now instrument the client so we have deeper insights into the performance of the system. 
To this end, for each POST and GET request:
* before sending the request, take a timestamp
* when the HTTP response is received, take another timestamp
* calculate the latency (end - start) in milliseconds
* Write out a record containing {start time, request type (ie POST), latency, response code}. CSV is a good file format.

Once all phases have completed, we need to calculate:
* mean response time for POSTs and GETs (millisecs)
* median response time for POSTs and GETs (millisecs)
* the total wall time
* throughput = requests per second = total number of requests/wall time
* p99 (99th percentile) response time for POSTs and GETs. [Here’s a nice article](https://www.elastic.co/blog/averages-can-dangerous-use-percentile) about why percentiles are important and why calculating them is not always
easy.
* max response time for POSTs and GETs

You may want to do all the processing of latencies in your client after the test completes, or you may want to write a separate program to run after the test has completed that generates the results. Check your results against those calculated in a spreadsheet. 

The client should calculate these and display them in the output window in addition to the output from the previous step, and then cleanly terminate. 

You will want to compare your wall time/throughput with the result from Part 1. Ideally they should be 'close' (within 5% or so). If they are more than 10% slower, perhaps you have some client behavior to capture the statistics that is not as efficient as it should be. 

## Submission Requirements
Submit your work to Canvas Assignment 1 as a pdf document. The document should contain:

1. the URL for your git repo. *Make sure that the code for the client part 1 and part 2 are in seperate folders in your repo*
1. a 1-2 page description of your client design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works
1. Client (Part 1) - run your client with 32, 64, 128 and 256 threads, with numSkiers=20000, numLifts=40 and numRuns=20. Include the outputs of each run in your submission (showing the wall time) and plot a simple chart showing the wall time by the number of threads. This should be a screen shot of your output window.
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

# Deadline: 10/7, 11.59pm PST 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
