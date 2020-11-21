# CS6650 Building Scalable Distributed Systems Fall 2020

# Assignment 4 - Scaling the Data Tier

## Overview

You work for Ikkyone  - a global acquirer of ski resorts that is homogenezing skiing around the world. Ikkyone  ski resorts all use RFID lift ticket readers so that every time a skier gets on a ski lift, the time of the ride and the skier ID are recorded.

In this course, through a series of assignments, we'll build a scalable distributed cloud-based system that can record all lift rides from all Ikkyone  resorts. This data can then be used as a basis for data analysis, answering such questions as:
* which lifts are most heavily used?
* which skiers ride the most lifts?
* How many lifts do skiers ride on average per day at resort X?

In Assignment 4, we'll focus on the database to make the application more scalable

## Distribute the Database

It's likely that the major performance bottleneck in your system is the database. So in this step, your aim is to remove this bottleneck.

You have complete freedom on how you do this. For example:
1. Configure a MySQL read replica and direct all GET requests to the replica
1. Deploy two (or more?) MySQL instances and shard the lift ride data across each instance
1. Move your database to an AWS hosted technology such as DynamoDB or Aurora
1. Choose a another database (e.g. MongoDB) that you'd like to learn and go for it!

## Load Testing

You know the story here by now. Test with 256 and 512 client threads. You will hopefully see some impressive throughput

Feel free to run tests again the server configuration from assignment 2 or 3, whichever gives you the best performance.

## Results Analysis

Compare the results you obtained in assignments 2 and 3 with the results in assignment 4 for 256 and 512 clients. 

Make sure you explain any anomolous characteristics, and if you need to re-run previous tests, just be clear about what you did.

## Submission Requirements
Submit your work to Canvas Assignment 4 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 4 server code
1. A 1-2 page description of your new database design. 
1. Test runs and results for 256 and 512 clients with your new database. Report the same statistics as assignment 3.
1. A 1-2 page comparison of the results from assignments 2, 3 and 4. 

## Grading:
1. Database design description (5 points) 
1. Test runs and results for 256 and 512 clients (5 points)  
1. Assignments results analysis (10 points) 

# Deadline: 12/13, 11.59pm PST 
