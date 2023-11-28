# CS6650 Fall 2023  Assignment 4

### Let's do Some Querying!

In this project we're going to query the number of likes and dislikes for an album.

Check out the [new API version](https://app.swaggerhub.com/apis/IGORTON/AlbumStore/1.2#) and the GET operation for likes and dislikes.

Your aim is simple. Query the number of likes and dislikes and try to get the best response time you can.

### What to do

Start with the client from Assignment 3

1. Add three new thread that simply query GET/review/{albumID} as fast as possible, using a random albumID

2. Start these threads after your first thread group has completed

3. Stop these threads as soon as all thread groups have completed

4. Output the usual statistics for the new GET operation, including the throughput you achieve from the three threads

Simple, eh?

### Issues to deal with

1. Form a group - 3 is ideal. Start with whatever code base you like.

2. Figure out a way to ensure the new GET threads always uses a valid album ID

### Submission

Your submission will be a presentation in the last class. You have 7 minutes at most, so aim for less than 10 slides.

1. It should describe:
2. Your system architecture - how all the major componenets fit together
3. Your results for all operations
4. Description of any experiments you performed to try and improve results - what did you change, what was the effect? Experiments are highly encouraged.

After your presentation one team member should submit the slides to canvas for Assignment 4 as a pdf, making sure all team members are listed on the first slide with the team name.

### Grading

Results: 20 points

Presentation Quality: 10 points

### Bonus Points:

* Team name makes instructor laugh!
* Wheel of Fortune points available during presentation

# Deadline: 14th December 11.59pm PST


