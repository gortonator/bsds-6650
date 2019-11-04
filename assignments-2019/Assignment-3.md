# CS6650 Fall 2019 Assignment 3
##Overview
You work for Upic - a global acquirer of ski resorts that is homogenezing skiing around the world. Upic ski resorts all use RFID lift ticket readers so that every time a skiier gets on a ski lift, the time of the ride and the skier ID are recorded.

In this course, through a series of assignments, we'll build a scalable distributed cloud-based system that can record all lift rides from all Upic resorts. This data can then be used as a basis for data analysis, answering such questions as:

- which lifts are most heavily used?
- which skiers ride the most lifts?
- How many lifts do skiers ride on average per day at resort X?

In Assignment 3, we're going to explore anoter Cloud platform and implement our system. It's time to learn Google Cloud Platform!

## Sign up for Google Cloud Platform (GCP)
If you haven't already, [sign up for GCP](https://edu.google.com/programs/credits/?modal_active=none). Follow the instructions in teh piazza post to redeem $50 credits.

## Server and Database Design
Assuming you used MySQL in assignment 2, moving to GCP should be pretty straightforward. We can use Cloud SQL for MySQL, the equivalent of AWS RDS.
To get started, work through the short tutorials [here](https://cloud.google.com/sql/docs/mysql/). These will enable you to provision a MySQL database and test your connection from the admin tools.

Initially at least, choose a db-f1-micro instance. Stop this when you are not using it as it consumes $$s. For load testing you might want to increase capacity - details on different instance are [here](https://cloud.google.com/sql/pricing#sql-server)

For your Java server, we're going to use Google App Engine (GAE), which is a serverless platform. This means GAE will do the load balancing and autoscaling for us.

Fortunately, GAE's Java support expects a servlet, which is luckily what you should have built in the first two assignments. In this case, you just need to build and load your server, and modify the connection code fo ryour MySQL instance.

Work through the getting started guides [here](https://cloud.google.com/appengine/docs/standard/java/), which includes instructions on how to set up your [development environment](https://cloud.google.com/code/docs/intellij/quickstart-IDEA).

Deploy your Java code as a standrad environment app. Then, as a first step, simply comment out calls to your DAO in one servlet method, deploy the application to GAE, and test you can call your servlet from POSTMAN.

## Connecting the Server to the Database
The instructions [here](https://cloud.google.com/sql/docs/mysql/connect-app-engine) give directions on how to modify your server to connect to you MySQL database. 

## Load testing
You should know what to expect by now. Run your client with 32, 64, 128 and 256 threads, with numSkiers=20000, numLifts=40 and numRuns=20.

## Submission Requirements
Submit your work to Blackboard Assignment 2 as a pdf document. The document should contain:

The URL for your git repo.
A 1-2 page description of your server design. Include major classes, packages, relationships, whatever you need to convey concisely how your client works
Single Server Tests - t Include the output window of each run in your submission (showing the wall time and performance stats) and plot a simple chart showing the throughput and mean response by the number of threads
Load Balanced Server Tests - run the client as above, showing the output window for each run. Also generate a plot of throughput and mean response time against number of threads.
Runtime Statistics Collection: Run a single client test with 256 client threads and wait for it to display its outputs. In the command line window, immediately issue a cURL/wget command on the /skiers POST/GET endpoints your have been testing. Hand in the command line window showing the client and cURL/wget outputs.
Grading:
Server implementation working (10 points)
Server design description (5 points) - clarity of description, good design practies used
Single Server Tests - (10 points) - 1 point per run output, 1 point for the chart, 5 points for sensible results.
Load Balanced Tests - (10 points) - 1 point per run, 1 point for the chart. 5 points for sensible results.
Runtime statistics - (10 points) - Successful implementation (5 points). Values of statistics from cURL/wget afetr test are sensible.
Deadline: 10/29, 3pm PST
Back to Course Home Page
