# CS6650 Buidliding Scalable Distributed Systems Fall 2019

## Assignment 4 - Experimentation Time

## Overview
In this course we have progressively built a distributed system that handles significant request loads and data volumes.

In this final assignment, you have the opportunity to propose your own extension to the code that you submitted for assignment 3.

Form teams of ideally 3 people (maximum) to undertake this assignment. You will all receive the same grade.

### Step 1 - Propose your Experiment
This is your opportunity to be creative. In general there are probably two ways to think about
what you could propose, namely:

1. Reimplement the assignment with different technology/design : For example, many
of you used a BlockingQueue in your server during data loading to decouple handling
client requests from writing to the database. You could propose to replace this queue
with Kafka, and see what effects this has on your solution (it will definitely add
reliability!). Or you could redesign your server to be a Storm topology which reads from a
data source (JMS/SQS) and processes the data to store in the database. Or you could
migrate your data layer to completely new database technology like Cassandra or
MongoDB? Or you could rewrite your server to be a lambda service? Even use a
different language?
2. Add new functionality : For example, you could write all the lift ride records to HDFS/S3 and
write a Hadoop job to determine the most busy lifts each day, or each hour? Or you
could use Hadoop/Spark to find the most used lift for each skier? It doesn’t need to be a
sophisticated machine learning algorithm (the data is random so there may not be much
to learn!), but just setting up the analysis jobs will be challenging.

In general, be creative, use something new that you have heard about in this class (or something
we haven’t covered that is designed for scaling}, and something you want to gain experience with. Have a look [here](https://gortonator.github.io/bsds-6650/assignments-2019/Assignment-4-ideas) for some reasonably specific ideas. Feel free to ask questions to elaborate ideas.

Write a short piazza post tagged with assignment4 that specifies:
1. Your team members
1. What you propose to do in terms of new learning objectives for the assignment (ie what do you intend to learn?)
1. What effect do you expect it to have? For example, using DB_X will provide lower latency queries then MySQL, or implementing the server using AWS Lambda will provide better throughput hen GAE.

The sooner you do this, the sooner we will be able to give you feedback on your idea. It might be well worth a sanity check! And we will give you a rating based on whether we think the project is interesting, appropriate, and if we think you will be able to successfully fulfill your aims!

### Step 2 - Build your proposed system modification
What you do in this step totally depends on your proposal, so good luck :)

### Step 3 - Assessment (20 points)
In the last class of semester, each team will have approx 8 minutes to present their proposal,
what happened when they tried to make the proposed changes, results, and what they learned.

## Assessment
### Step 1 - Proposal - post on piazza:
1. Clearly expressed: (2 points)
1. Suitably ambitious (3 points)
### Step 2
1. Presentation quality (2 points)
1. Detailed explanation of the changes you made (8 points)
### Step 3
1. Explain how you assessed/analyzed the effectiveness of the changes. Was it faster? Slower? More reliable? Easier to build/deploy? (7 points)
1. What you learned - both positive and negative (3 points)

## Deadline:
Each team member should submit a pdf of their group presentation on blackboard before class on December 10th.

## Grading and Submissions
There are 25 points available for this assignment
Submit a pdf file to blackboard with your presentation slides.
Also submit a short report (max 4 pages) that gives more detail on your work and findings. This can include more details on specifci designs, configuration settings, additional results, issues that arose that made it difficult/impossible to rwach your goals. 
Step 1 - 5 points
Step 2 - 10 points
Step 3 - 10 points

## Deadline December 10th in class
