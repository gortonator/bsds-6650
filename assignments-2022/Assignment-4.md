# CS6650 Fall 2022  Assignment 4

## Time to do some reading ....

The first three assignments have focused on writing data from POST requests. Finally, in this assignment, we will do some reading!

You can work on this assignment in groups of up to 4 people.

### Step 1 Populate the Databases

We're going to run assignment 3 to populate your databases. First, empty your databases.

You should then do 3 seperate client runs, as follows:

* keep resort and season constant in all 3 runs
* vary the day value for each run, namely {1, 2, 3}
* keep the number of skiers, lift rides as default values for first 3 assignments
* Thread configuration of your choice

### Step 2 Implement the GET APIs

Use the version of the API [here](https://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/1.16)

You need to implement:

* GET/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers

* GET/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}

* GET/skiers/{skierID}/vertical
  
  Test these with Postman or your fave API testing tool

### Step 3 Load test the GETs

In this step you'll load test the new GET APIs. To generate a load testing client, we'll use a load generating tool.

[Apache JMeter](https://jmeter.apache.org/) is widely used and straightforward to set up, so this is the recommended tool.

Use JMeter to create test cases. [These instructions](https://jmeter.apache.org/usermanual/build-web-test-plan.html) provide the basics of what you need to do.The requirements are as follows. For each of the three GET requests, define a test with a thread group with:

* 128 threads
* 50 iterations
* 10 second ramp up time

Randomly generate a skierID and dayID for each request.

JMeter produces a bunch of statistics for each run. Experiment and see how low you can get the response times and p99s.

### Submission requirements

You'll present your results in class on April 26th. Plan 5-ish minutes, with a structure as follows:

* Team Name (must be funny)
* 1-2 slides showing your architecture, how microservices communicate, deployment
* 1-2 slides on data model used
* 2-3 slides results for JMeter tests
* 1 slide on what you would do to make it even better

Also submit your slides and results as pdfs to canvas. You may refine your results and presentation after the presentation if desired.

### Grading

JMeter results for all 3 test cases: 20 points

Presentation: 10 points

Bonus Point: Team name makes instructor laugh!

# Deadline: Wed 14th December 11.59pm PST
