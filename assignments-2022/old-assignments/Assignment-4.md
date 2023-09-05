# CS6650 Spring 2023  Assignment 4

## Let's Build the CQRS Patern

We're going to explore a standard architecture pattern for handling mixed workloads. It's called the CQRS Pattern. Check out [this article](https://blog.bitsrc.io/how-i-redesigned-the-backend-to-quickly-handle-millions-of-reads-and-writes-58cfe989e6f8) for an explanation. 

You can work on this assignment in groups of ideally 3, and max 4 people. 

### Step 1 Redesign Your Application

The CQRS pattern bascially requires a seperate execution path and data store for reads and writes. As a group you need to figure out how to refactor your solutions into a group-based design.
The resulting systems should implement CQRS. 

You have complete freedom to do this however you like. Don't like RMQ? Scrap it. Wanna play with MongoDB leader-followers? Go for it! Bonus points for add and weird (er ... I mean creative!) solutions!

For example, [here's a recipe](https://docs.aws.amazon.com/prescriptive-guidance/latest/modernization-data-persistence/cqrs-pattern.html) for using DynamoDB and a relational DB linked by a Lambda function. Implement this. It's cool :)
### Step 2 Load the Database

Empty your database and then run a client that generates 500k swipe events. This should be the client from Assignment 2. You don't need the GET thread from assignmnet 3.

You'll probably want to optimize this load as low latency POSTs will help in the REAL TEST  :) (see next)

### Step 3 Load test the System

In this step you'll load test the system. To generate a load testing client, we'll use a load generating tool.

[Apache JMeter](https://jmeter.apache.org/) is widely used and straightforward to set up, so this is the recommended tool.

Use JMeter (or tool of your choice) to create test cases. [These instructions](https://jmeter.apache.org/usermanual/build-web-test-plan.html) provide the basics of what you need to do.

The requirements are as follows. 

* Create 3 thread groups, one for each API you have built (ie 2 GETs and 1 POST)
* The GET thread groups should each have 128 threads, execute 500 iterations and have a 10 second ramp up time
* The POST thread group should have 64 threads, execute 250 iterations and have a 10 second ramp up time

Randomly generate a swipe event data/requests as per assignment 3 (or whatever makes sense!)

JMeter produces a bunch of statistics for each run. Experiment and see how low you can get the response times and p99s. We're really looking for fast, stable GETs.

### Submission requirements

You'll present your results in class on April 18th. 

Plan 7 minutes maximum, with a structure as follows:

* Team Name (must be funny)
* 1-2 slides showing your architecture, how it support CQRS, how it is deployed
* 1-2 slides on the database and data model used
* 2-3 slides results for JMeter tests
* 1 slide on what you would do to make it even better

Also submit your slides and results as pdfs to canvas. Only one team member needs to submit, just make sur eall your names on it so you allm get the points. 

You may refine your results and presentation after the presentation if desired until the assigment deadline.

### Grading

JMeter results: 20 points

Presentation: 10 points

Bonus Points: 
* Team name makes instructor laugh!
* Wheel of Fortune points available during presentation

# Deadline: April 26th  December 11.59pm PST
