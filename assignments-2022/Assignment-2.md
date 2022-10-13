# CS6650 Spring 2022  Assignment 2


## Building the Server
This assignment builds on assignment 1. Your client won't change (unless it has a bug in it!) beyond tuning thread pool counts to optimize throughput. 

You will implement processing logic in your server and post the result to a queue for subsequent consumption and processing.

## Step 1 - Implement the Server
In this assignment, you will fully implement the doPost() method in your servlet to:

1. fully validate the URL and JSON payload
1. if valid, format the incoming data and send it as a payload to a remote queue and then return success to the client

You can choose your own queue technology. RabbitMQ is an obvious one, AWS SQS another if you are keen. If you choose RMQ, check out the Addendum at the end of this file for multithreading hints. 

Make sure you deploy RabbitMQ on its own EC2 instance. You can find various installation instructions [here](https://www.rabbitmq.com/download.html). We have tested the Ubuntu install and it works fine.

Carry out testing with a relatively small number of messages as you don't have a consumer yet. Clear out the queues regulalry.

## Step 2 - Implement a Consumer
Implement a plain old Java program to pull messages off the queue. 

This program simply receives messages from the queue and keeps a record of the individual lift rides for each skier in a hash map. 

Your aim is to consume messages, ideally, as quickly as they are produced. 
This means your consumer will need to be multithreaded and your hash map thread safe.

Run the consumer on its own instance, connecting remotely to the broker queue.

## Load Testing

Your aim here is to find the 'best' application configuration in terms of responsiveness to the client and managing queue size. 

You should have the servlet, RMQ broker and consumer all running on their own EC2 instances.

Report the same metrics as in assignment 1. Your aim again is to maximize client throughput.

The challenge is that you may have handled all requests from the client at the servlet, but still have say 100K in the queue waiting for consumption. This is unlikely to be your optimal configuration as RMQ struggles with long queue lengths. Hence you need to balance production and consumption rates, such that **production_rate** ≈ **consumption_rate**

You can use the RabbitMQ management console to track the number of messages in the queue, and producers and consumer rates.

The questions you need to explore are:

* How many client threads are optimal after phase 1 to maximize throughput?
* How many queue consumers threads do I need to keep the queue size as close to zero as possible? Less than a 1000 max is a great target, but the main aim is to ensure the queue length doesn't continually grow, then shrink, giving a 'pointy' queue length profile, ie /\\. An increase to a plateau is fine, ie /¯¯¯¯\\. If the plateau is less than a 1000, you are in great shape!

### Incorporate Load Balancing

One free tier server for your servlets will probably get pretty busy, so you will want to introduce load balancing. 

You can set up [AWS Elastic Load Balancing](https://aws.amazon.com/elasticloadbalancing/features/?nc=sn&loc=2) using either _Application_ or _Network_ load balancers. 
Enable load balancing with say 2 and 4 free tier EC2 instances and see what effect this has on your performance.

A tutorial [here](https://docs.aws.amazon.com/elasticloadbalancing/latest/application/application-load-balancer-getting-started.html) should help.

### Tune the System

Run your client against the load balanced servlets and see what effect it has on throughput and queue length. What do you have to do to keep the queue length short. Add more consumer threads? Use more powerful instances for the consumer with 2 vCPUs? 

## Submission Requirements
Submit your work to Canvas Assignment 2 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 2 server code
1. A 1-2 page description of your server design. Include major classes, packages, relationships, how messages get sent/received, etc
1. Test run results (command lines showing metrics, RMQ management windows showing queue size, send/receive rates) for a single servlet showing your best throughput. 
1. Test run results (command lines showing metrics, RMQ management windows showing queue size, send/receive rates) for a load balanced servlet showing your best throughput. 

## Grading:
1. Server and consumer  implementations  (15 points)
1. Server design description (5 points) - clarity of description, good design practies used
1. Results (10 points) - single instance tests. Overall throughput, short queue sizes and profile
1. Results (10 points) - load balanced instance tests. Overall throughput (should be greater than no load balancer), short queue sizes and profile

# Deadline: 10/28 11.59pm PST 

## Addendum: Multithreading and RabbitMQ

RabbitMQ and multithreading needs a few considerations. Read on ....

The basic abstraction that needs to be operated on by each thread is the channel. This means:

In your servlet (or equivalent if not using a servlet):

1. In the init() method, initialize the connection (this is the socket, so is slow)
1. In the dopost(), create a channel and use that to publish to RabbitMQ. Close it at end of the request.

This should work fine, although the [documentation](https://www.rabbitmq.com/api-guide.html#concurrency) say channels are meant to be long-lived and caution again churn. 

So a better solution would be to create a channel pool that shares a bunch of pre-created channels (in .init()) to form a connection pool. 

Roll your own is not too hard, but apache commons has a [generic pool implementation](http://commons.apache.org/proper/commons-pool/examples.html) that you could build on.
The reading has an example of how to do this.

Another approach to implementing a channel pool would be to use a BlockingQueue. Not too tricky ... give it a try!

On the consumer side, you probably want a multi-threaded consumer that just gets a message and writes to the hash map. In this case you can just create a channel per thread and all should be fine. 

There's an excellent write up that describes the complexities of multi-threaded RMQ clients [here](http://moi.vonos.net/bigdata/rabbitmq-threading/)

And [here's](https://github.com/gortonator/bsds-6650/tree/master/code/week-6) some sample code you can work from. 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)



