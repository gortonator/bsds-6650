## Lab 10 - Quiz make up exersise

Work in your allocated group to implement and demonstrate the problem below. 

Each group needs to create a piazza post with the title "Test Make-Up Exercise: <Team Name>". In the post provide the names of each team member.

You can engage with TAs/instructors on any issue.

As soon as you are ready to present, get a TA/instructor to show your demo to.

Time: You have until the end of class to present your working solution or explain your approach and progress. 

### Grading:
* Successful solution demonstration: 10 points
* Minor run time errors but close: 7.5 points
* Good approach but implementation not complete: 5 points
* Well, your turned up and tried ;): 2.5 points

### The Problem: Implement the Bully Algorithm
Your group task is to implement the Bully algorithm for leader election. A simple description of the algorithm is [here](http://denninginstitute.com/workbenches/bully/bullyalg.html).

You are pretty free to do this in anyway you choose, as long as the following requirements are met:

1. Each node is represented by a seperate process. They can run on the same node or different nodes. Doesn't matter.
1. On startup, each node process takes 2 arguments, (1) the number of nodes, (2) the id for the local node
1. All nodes are able to communicate with all other nodes. This is necessary to implement the algorithm. How they communicate is your choice - Java RMI? RabbitMQ? sockets?
1. Use a node ping/timeout period of 5 seconds.

To demonstrate your solution, start 5 nodes and call an instructor to demonstrate what happens when you kill the master.

To facilitate grading, make sure each node prints out diagnostics. For example: 
* The leader should periodically print out "N:I am Leader"
* Followers should print out "N: I am a follower, N is the Leader"
* When an election is started, nodes should print out their actions state, eg "N:started election", "N: Reply from node N+2, returning to Follower", etc

Good luck!!!

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)

