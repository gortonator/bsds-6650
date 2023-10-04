# CS6650 Fall 2023  Assignment 1

### Overview

This is part 1 of a series of assignments that will guide you through designing a scalable distributed systems running on AWS.

We're going to implement a simple music service that stores data about albums. In this assignment we'll just build the simple API and a client that we can use to test performance.

### Step 1 - Build the servers

The API specification is built using OpenAPI 3.0, and the Swagger toolset. It's [here](https://app.swaggerhub.com/apis/IGORTON/AlbumStore/1.0.0). You'll need to implement the endpoints that are defined for POST and GET. 

In this assignment, you are just implementing API stubs that:

1. do simple validation (check types, arguments)
2. return a fixed album key from POST and constant data from the GET (the API has an example you can use)

You should implement the API as:

1. a Java servlet that has _doPost_ and _doGet_ methods. Good simple illustrations of how to handle JSON request payloads in Java are [here](https://edwin.baculsoft.com/2011/11/how-to-create-a-simple-servlet-to-handle-json-requests/) and [here](https://www.baeldung.com/servlet-json-response).
2. A Go Server. The Swagger Web page for the API has an _Export_ option that will generate a skeleton Go service to hopefully make your life easy! Or use the Go Tutorial from Week 0 as a guide to implement the server with Gin. 

Test the API with [POSTMAN](https://www.getpostman.com/downloads/) or an equivalent HTTP testing tools. You should first test these APIs on servers running on your laptop (ie localhost). [Here's an image](https://gortonator.github.io/bsds-6650/assignments-2022/nmtb.png) you can use for testing. 

Once the servers are running locally, ssh them to an AWS free tier instance, running in Oregon, that you have installed Java/Tomcat v8/9.0 (not 10).

If you cross compile your Go server on your laptop (instructions in labs), you should just be able to transfer the executable and run it on AWS. 

### Step 2 - Build the client (Part 1)

Now the fun starts. We want a client that can exert request loads on our servers so we can compare their performance when running on AWS.  

First, just write a trivial client to test connectivity and get to grips with client APIs. See the **Client API Implementation** section below for some details/options.

Once your have the client API calls working (just like in POSTMAN), write a Java programs that:

1. accepts 4 arguments, ie threadGroupSize = N, numThreadGroups = N,  delay = N (seconds), and IPAddr = server URI to to test against.
2. On startup, the program creates 10 threads, and each thread calls the POST API followed by the GET API 100 times in a simple loop. IPAddr is the base address of your server URI. This is basically a hardcoded initialization phase.
3. Take a _startTime_ timestamp
4. Once all 10 threads have completed, startup _threadGroupSize_ threads, each of which sends 1000 POST/GET APIs pairs (ie 2000 total)
5. Once all the threads are running, wait for a period of _delay_ seconds, then start another (identical) thread group.
6. Repeat the above step (wait _delay_ seconds, start a thread group) until you have started _numThreadGroups_.
7. When **_all the threads_** from all thread groups have completed, take an _endTime_ timestamp

Once all thread have completed, you programs should output in the command window:

1. Wall Time - the time taken to complete the test in seconds
2. Throughput - the number of requests processed every second.

#### Handling Errors

If the client receives a 5XX response code (Web server error), or a 4XX response code (from your servlet), it should retry the request up to 5 times before counting it as a failed request. This probably means your server or network is down, or very overloaded.

Note testing on the College network might trigger firewall rules that block your requests. You may need to do proper load testing at home, or a coffee shop, or bar, depending on your state of mind ;)

### Step 3 - Load test the servers

Use your client to load test the Java and Go servers running on an AWS instance. You should run 3 test loads for each server, running the client on your laptop i.e.:

1. threadGroupSize = 10, numThreadGroups = 10, delay = 2 
2. threadGroupSize = 10, numThreadGroups = 20, delay = 2
3. threadGroupSize = 10, numThreadGroups = 30, delay = 2

Plot the throughput for the tests and compare the Go and Java implemenation's performance. 

### Step 4 - Let's get more statistics (Client Part 2)

With your load generating client working wonderfully, we want to now instrument the client so we have deeper insights into the performance of the system.

To this end, for each (POST/GET) request:

* before sending the request, take a timestamp
* when the HTTP 200/201 response is received, take another timestamp
* calculate the latency for the request (end - start) in milliseconds
* Write out a record containing {start time, request type (ie POST/GET), latency, response code}. CSV is a good file format. Or you may have enough memory. May ... good luck ;)

Once all threads/thread groups are completed, calculate for both POST and GET:

* mean response time (millisecs)
* median response time (millisecs)
* p99 (99th percentile) response time. [Here’s a nice article](https://www.elastic.co/blog/averages-can-dangerous-use-percentile) about why percentiles are important and why calculating them is not always easy. (millisecs)
* min and max response time (millisecs)

The client should calculate these and display them in the output window in addition to the output from the previous step, and then cleanly terminate.

You want to do all the processing of latencies to generate the statistics in your client after the test completes. 

Carefully compare wall times with identical test loads from Step 3. If you see more than a 5% degradation, you need look carefully at your implementation and make it more effiecnet, as you do not want to reduce the load exerted on the server.

### Step 5 Load test the servers again

Repeat Step 3 above, plotting results and comparing the Go and Java performance.  The results should look similar to Step 3. 

For each test include a command window image showing your client output and the calculated results for each test.

### Step 6: Plot Performance

You need to plot a graph that shows the average throughput of requests for the period (wall time) of your tests. To do this, create a chart that has:

- x-axis values: unit is seconds, from 0 to test wall time, with intervals of one second

- y-axis values: unit is throughput/second, showing the number of requests completed in each second of the test

You can create the chart programmatically of just dump the values into a spreadsheet and go for your life! Just show one chart for the (threadGroupSize = 10, numThreadGroups = 30, delay = 2) test configuration for either server.

## Submission Requirements

Submit your work to Canvas Assignment 1 as a pdf document. The document should contain:

1. the URL for your git repo. *Make sure that the code for the client part 1 and part 2 are in seperate folders in your repo*

2. a 1-2 page description of your client design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works.

3. Client (Part 1) - A Plot showngthe throughput for the tests comparing teh two servers. This should also include a screen shot of your output window with your wall time and throughput for each of the 6 tests. 

4. Client (Part 2) - run the client as per Part 1, showing the output window for each run with the specified performance statistics listed at the end, and a plot comparing the two servers.

5. The plot  of your throughput over time for a single test

## Grading:

1. Server implementations working (10 points)

2. Client design description (5 points) - clarity of description, good design practies used

3. Client Part 1 - (10 points) - Output window showing best throughput. Somewhere around 2k/sec should be a minimum target at higher loads

4. Client Part 2 - (10 points) - 5 points for throughput within ~5% of Client Part 1. 5 points for calculations of mean/median/p99/max/throughput (as long as they are sensible).

5. Step 6: Plot of throughput over time (5 points)

### Additional Useful Information

### Client API Implementation

First you need to get a Java client to call your server APIs. You can generate a client API from the Swagger specification page. Look at:

*Export-Client SDK-Java* options (web page, top right)

Unzip the client and follow the instructions in the README to incorporate the generated code in your client project.

The generated code contains classes and methods for calling the server APIs.

Write a simple test that calls the API before proceeding, to establish that you have connectivity. The examples in the README and documentation are your friends ;).

To connect to your remote server on EC2 you need to call an ApiClient methods (hint - setBasePath(...)) . It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues. You then pass this ApiClient object to your specific xxxApi object. Again, look at the constructors for xxxApi objects. **(Note - you should never modify generated code - in this case you don't need to!)**

If you don't want to figure out the Swagger client, you can use the [Java 11 HTTP client classes](https://openjdk.org/groups/net/httpclient/intro.html) or the [Apache Java HTTP API](https://hc.apache.org/index.html). These are both pretty stright forward, especially with AI to help!

### Building Swagger Client with Java 11

You need to modify your POM, add the following dependencies:

```
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
```

### Using StudServer for Go generated using Swagger

if you thinking of using the studserver generated from The Swagger Web page for the API. 

Here is one way to make it running in the local. 

First way, set up module import

1. In the go folder where router.go file resides, run go mod init command and go mod tidy command to create and update the go.mod file

2. In the folder where main.go file resides,
- run go mod init command

- change the sw "./go" to something like sw "example.com/router"

- run go mod edit -replace example.com/router=/go   command. This will tell the go to look for folder inside /go when reference to "example.com/router"

- run go mod tidy command
3. Finally, try run the main.go file using go run command. 

It is highly recommended that you go through the tutorial below to understand how to import other module from local machine. 

https://go.dev/doc/tutorial/create-module

Alternative way, places all files in the same module (folder)

1. Move all the .go file under the /go folder into the same folder for the main.go file 

2. change the package name of all go file to be the same as the package name of the main.go file 

3. Run go mod init command and go mod tidy command

4. Run main.go 

If you opt for this route, as you start writing more functions and structs in the same module, your folder might become very big with lots of file. Which may not be a tidy way to do things 

#### Important Notes

Note the studserver generated doesnt contain any logics. You probably still need additional work to amend it to suit your need. Especially when you need to process the multipart file from the request. 

And the studserver is not using gin framework, which may be slower. 

The alternative is to code the server from scratch based on the API. 

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

## Calculating Percentiles Using Histograms

You can calculate approximate percentiles efficiently using histograms. Google around - there are lots of sites that explain the approach. I haven't tried [this implementation](https://github.com/IBM/HBPE/blob/master/README.md), but it sounds promising.
