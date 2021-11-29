# CS6650 Fall 2021  Assignment 4

## Time to do some reading ....
The first three assignments have focused on writing data from POST requests. Finally, in thie assignment, we will do some reading!

### Step 1 Populate the Databases
First, we're going to run assignment 3 to populate your databases. Start by emptying your databases.

You should then do 3 seperate client runs, as follows:
* keep resort and season constant in all 3 runs
* vary the day value for each run, namely {1, 2, 3}
* keep number of skiers, lift rides as default values for first 3 assignments
* maximum 256 threads for each test

### Step 2 Implement the GET APIs
 A new version of the API is available [here](https://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/1.15)
 
 You need to implement:
 * GET/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers
 * GET/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
 * GET/skiers/{skierID}/vertical
 
 Test these with Postman or your fave API testing tool
 
### Step 3 Load test the GETs
In this step you'll load test the new GET APIs. To generate a load testing client, we'll use a load generating tool. 

[Apache JMeter](https://jmeter.apache.org/) is widely used and straightforward to set up, so this is the recommended tool. 

Use JMeter to create test cases. [These instructions](https://jmeter.apache.org/usermanual/build-web-test-plan.html) provide the basics of what you need to do.
The basic requirements are as follows. For each of the three GET requests, define a test with a thread group with:
* 100 threads
* 50 iterations
* 5 second ramp up time

Randomly generate a skierID and dayID for each requests.

JMeter produces a bunch of statistics for each run. Experiment and see how low you can get the response times and p99s.
 
 
### Submission requirements
You'll present your results in class on December 14th. Plan 5 of so minutes, mostly focus on test results and any design/technical issues you think you should highlight.

Also submit your slides and results as pdfs to canvas. You may refine your results and presentation after the presentation if desired. 
 
### Grading
JMeter results for all 3 test cases: 20 points
Presentation: 10 points

# Deadline: December 15th 11.59pm PST 


