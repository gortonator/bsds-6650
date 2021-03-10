# CS6650 Spring 2021 Assignment 3

## Overview

A old decrepit supermarket chain has just had a huge injection of cash from a mystery multi-billionaire and is embarking on a rapid acquisition strategy to buy up new stores nationwide. 
These new stores need to be integrated into their existing business systems. They hire your development business to help them undertake this massive exercise.

In Assignment 3, we'll build upon your server and database design in Assignment 2 to make the application more responsive to client requests, and introduce a new microservice.

## Implement the Server 
 
 A heavy concurrent write load exerts considerale stress on a relational database, as many of you have discovered in Assignment 2.
 
 In this step modify your server to write all new POST request payloads to a queue instead of the database. 
 Design a producer-consumer based solution to write purchase data to the database 'eventually'. 
 
 In addition, create a new microservice called *Store*. Store should also receive every purchase record and create a data structure that records the quantity of each item purchased at each store.
 The Store microservice should be designed to answer two queries:
 
 1. What are the top 10 most purchased items at Store N
 2. Which are the top 5 stores for sales for item N
 
 A new version of the Swagger API is [here](https://app.swaggerhub.com/apis/gortonator/GianTigle/1.11). Use this as the service specification.
 
 In this assignment you can build data structures for *Store* in memory to answer these queries. No database is required. 
   
 You may choose a queueing solution of your choice. RabbitMQ is an obvious one, AWS SQS is equally simple, but beware of [costs](https://aws.amazon.com/sqs/pricing/) of course.
 
 Regardless, your solution needs to be aware of the possibility of losing messages due to a queue node crash. Experiment with persistent versus non-persistent queue configuration to explore the inherent performance trade-offs.
 
 If you use RabbitMQ, there are some considerations to address concerning multithreading. These are explained in an Addendum at the bottom of this assignment. 

## Load Testing
 
Your aim here is to find the best application configuration in terms of throughput for 256 and 512 client threads. You do not need to make changes to the client - use same load profile as in assignmemt 2
 
The questions you need to explore are:
* Do I need load balancing? Or can my system work with 1 free-tier (or slightly upgraded) server
* How many consumers nodes do I need?
* What messaging system design should I use?
 
## Submission Requirements
Submit your work to Canvas Assignment 3 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 3 server code
1. A 1-2 page description of your messaging and server architecture design. Include database schema, major classes, packages, relationships, how read requests get processed, etc
1. A commparison of the results for 256 client threads between this assignment and assignment 2, either single server or load balanced based on your design and performance. 
1. Results for a test with 512 client threads
1. An example of the output from querying the Store microservice. Use cURL to call the GETs after a test run to illustrate the outputs. 

## Grading:
1. Asynchronous servers implementation working (10 points)
1. Server design description (5 points) - clarity of description, good design practicies used
1. Results comparison (10 points) - throughput, persistent/non-persistent queues, different instances, basically describe and analyze what experiments you did
1. Results of a run with 512 client threads (5 points) - successful run (3 points), comparison with 256 client thread test (2 points)
1. Outputs from calling GET on the Store microservice (5 points each)

# Deadline: 4/5, 11.59pm PST 

## Addendum: Multithreading and RabbitMQ

RabbitMQ and multithreading needs a few considerations. Read on ....

The basic abstraction that needs to be operated on by each thread is the channel. This means:

In your servlet:

1. In the init() method, initialize the connection (this is the socket, so is slow)
1. In the dopost(), create a channel and use that to publish to RabbitMQ. Close it at end of the request.

This should work fine, although the [documentation](https://www.rabbitmq.com/api-guide.html#concurrency) say channels are meant to be long-lived and caution again churn. 

So a better solution would be to create a channel pool that shares a bunch of pre-created channels (in .init()) just like your Database connection pool. 

Roll your own is not too hard, but apache commons has a [generic pool implementation](http://commons.apache.org/proper/commons-pool/examples.html) that you could build on.

On the consumer side, you probably want a multi-threaded consumer that just gets a message and writes to the database. In this case you can just create a channel per thread and all should be fine. 

There's an excellent write up that describes the complexities of multi-threaded RMQ clients [here](http://moi.vonos.net/bigdata/rabbitmq-threading/)

And [here's](https://github.com/gortonator/bsds-6650/tree/master/code/week-6) some sample code you can work from. 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
 
 
 
 
 
