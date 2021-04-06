# CS6650 Building Scalable Distributed Systems Spring 2021

# Assignment 4 - Time to Experiment!

## Overview

This is a chance for you to experiment and learn new stuff, both design and technology.

The basic problem statement is the same as Assignment 3. So that's easy ....

As you've hopefully learned though, there's many ways to design this system. You task in this assignment is to try something new and see if you can improve scalability.

So here's some ideas:
1. Scale out your purchase database. Maybe shard the purchases across multiple instances? Use DynamoDB? Use MongoDB?
1. Replace RabbitMQ with a partitioned Kafka broker.
1. Implement a distributed database for your Store microservice? You may want to test this with a GET-heavy load (so feel free to change the client test case)
1. Use Apache Flink to provide a streaming-based answer to your GETs. The [data analytics section in this outline](https://flink.apache.org/usecases.html) provides hints on how this might work. 

## Results Analysis

Compare the results you obtained in assignments 2 and 3 with the results in assignment 4 for 256 and 512 clients. 

Make sure you explain any anomolous characteristics, and if you need to re-run previous tests, just be clear about what you did.

## Submission Requirements
Submit your work to Canvas Assignment 4 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 4 server code
1. A 1-2 page description of your new experiment design. 
1. Test runs and results for 256 and 512 clients with your new database. Report the same statistics as assignment 3.
1. A test run with 1024 clients showing the solution maintains scalability (this will be hard!!)
1. A 1-2 page comparison of the results from assignments 2, 3 and 4. 

## Grading:
1. Experiment design description (5 points) 
1. Test runs and results for 256 and 512 clients (5 points)  
1. Successful test with 1024 clients that shows the solution is still scalable (5 points)
1. Assignments results analysis (10 points) 

IMPORTANT NOTE - AWS Educate classroom is set to expire (unfortunately) on 23rd April. so make sure you complete your experiments by then!
# Deadline: 4/26, 11.59pm PST 
