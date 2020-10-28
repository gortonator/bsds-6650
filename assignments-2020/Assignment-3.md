# CS6650 Fall 2020 Assignment 3

## Overview

You work for Ikkyone  - a global acquirer of ski resorts that is homogenezing skiing around the world. Ikkyone  ski resorts all use RFID lift ticket readers so that every time a skier gets on a ski lift, the time of the ride and the skier ID are recorded.

In this course, through a series of assignments, we'll build a scalable distributed cloud-based system that can record all lift rides from all Ikkyone  resorts. This data can then be used as a basis for data analysis, answering such questions as:
* which lifts are most heavily used?
* which skiers ride the most lifts?
* How many lifts do skiers ride on average per day at resort X?

In Assignment 3, we'll build upon your server and database design in Assignment 2 to make the application more responsive to client requests.

## Implement the Server 
 
 A heavy concurrent write load exerts considerale stress on a relational database, as many of you have discovered in Assignment 2.
 
 In this step modify your server to write all new POST request payloads to a queue instead of the database. Design a producer-consumer based solution to 
 write life ride data to the database 'eventually'. 
 
 You may choose a queueing solution of your choice. RabbitMQ is an obvious one, AWS SQS is equally simple, but beware of [costs](https://aws.amazon.com/sqs/pricing/) of course.
 
 Regardless, your solution needs to be aware of the possibility of losing messages due to a queue node crash. Experiment with persistent versus non-persistent queue configuration to explore the inherent performance trade-offs.
 
## Load Testing
 
Your aim here is to find the best application configuration in terms of throughput for 256 and 512 client threads. You do not need to make changes to the client - use same load profile as in assignmemt 2
 
The questions you need to explore are:
* Do I need load balancing? Or can my system work with 1 free-tier (or slightly upgraded) server
* How many consumers nodes do I need?
* GETs still need to access the database? Can a cache be used to make GETs faster? How would the cache be updated?
 
## Submission Requirements
Submit your work to Canvas Assignment 3 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 3 server code
1. A 1-2 page description of your server design. Include database schema, major classes, packages, relationships, how read requests get processed, etc
1. A commparison of the results for 256 client threads between this assignment and assignment 2, both single server and load balanced
1. Results for a test with 512 client threads

## Grading:
1. Asynchronous server implementation working (10 points)
1. Server design description (5 points) - clarity of description, good design practies used
1. Results comparison (10 points) - throughput, persistent/non-persistent queues, different instances, basically describe and analyze what you did
1. Results of a run with 512 client threads (5 points) - successful run (3 points), comparison with 256 client thread test (2 points)


# Deadline: 11/23, 11.59pm PST 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
 
 
 
 
 