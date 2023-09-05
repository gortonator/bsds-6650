# CS6650 Spring 2023 Assignment 2

## Building the Server

This assignment builds on assignment 1. Your client won't change (unless it has a bug in it!) beyond tuning thread pool counts to optimize throughput.

You will implement simple business logic in your server and post the result to a queue for subsequent consumption and processing.

## Step 1 - Implement the Server

In this assignment, you will fully implement the doPost() method in your servlet to:

1. fully validate the URL and JSON payload
2. if valid, format the incoming **Swipe **data and send it as a payload to a remote queue and then return success to the client

You can choose your own queue technology. RabbitMQ is an obvious one, AWS SQS another if you are keen. If you choose RMQ, check out the Addendum at the end of this file for multithreading hints.

Make sure you deploy RabbitMQ on its own EC2 instance. You can find various installation instructions [here](https://www.rabbitmq.com/ec2.html). The ones [here](https://www.cherryservers.com/blog/how-to-install-and-start-using-rabbitmq-on-ubuntu-22-04), for Ubuntu have been tested recently and work fine.

Carry out testing with a relatively small number of messages as you don't have a consumer yet. Clear out the queues regulalry using the RMQ management console.

## Step 2 - Implement Consumers

Implement two plain old Java programs to pull messages off the queue. Each needs to consume every published message, and store it in a data structure that is designed to support two different access patterns, namely:

1. Given a user id, return the number of likes and dislikes this user has swiped

2. Given a user id, return a list of 100 users maximum who the user has swiped right on. These are potential matches.

Your aim is to consume messages, ideally, as quickly as they are produced, as ths will give best throughput on RMQ. This means your consumer will need to be multithreaded and your data structures thread safe.

Run the consumers on seperate or a single (probably not free tier when load testing) instance, connecting remotely to the broker queue.

## Load Testing

Your aim here is to find the 'best' application configuration in terms of responsiveness to the client and managing queue size.

You should have the servlet, RMQ broker and consumers all running on their own EC2 instances.

Report the same client metrics as in assignment 1. Your aim again is to maximize client throughput while minimizing queueing delays. 

The challenge is that, for example, you may have handled all requests from the client at the servlet, but still have say 100K in the queue waiting for consumption. This is unlikely to be your optimal configuration as RMQ struggles with long queue lengths. Hence you need to balance production and consumption rates, such that **production_rate** ≈ **consumption_rate. ** When this condition is met, queue lengths remain small and systems perform predictably and reliably.

You can use the RabbitMQ management console to track the number of messages in the queue, and producers and consumer rates.

The questions you need to explore are:

* How many client threads are optimal  to maximize system throughput?
* How many queue consumers threads are needed to keep the queue size as close to zero as possible? Less than a 1000 max is a great target, but the main aim is to ensure the queue length doesn't continually grow, then shrink, giving a 'pointy' queue length profile, ie /\\. An increase to a plateau is fine, ie /¯¯¯¯\\. If the plateau is less than around a 1000, you are in great shape!

### Incorporate Load Balancing

One free tier server for your servlets will probably get pretty busy, so you will want to introduce load balancing.

You can set up [AWS Elastic Load Balancing](https://aws.amazon.com/elasticloadbalancing/features/?nc=sn&loc=2) using either _Application_ or _Network_ load balancers. Enable load balancing with 2 free tier EC2 instances and see what effect this has on your performance.

A tutorial [here](https://docs.aws.amazon.com/elasticloadbalancing/latest/application/application-load-balancer-getting-started.html) should help. Remember to create [AWS templates](https://docs.aws.amazon.com/autoscaling/ec2/userguide/create-launch-template.html) for your instances.

### Tune the System

Run your client against the load balanced servlets and see what effect it has on throughput and queue length. What do you have to do to keep the queue length short. Add more consumer threads? Use more powerful instances for the consumers with 2 vCPUs? Increase RMQ server memory or instance type?  There's a lot of variables here so do your best. 

## Submission Requirements

Submit your work to Canvas Assignment 2 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 2 server code
2. A 1-2 page description of your server design. Include major classes, packages, relationships, how messages get sent/received, etc
3. Test run results (command lines showing metrics, RMQ management windows showing queue size, send/receive rates) for a single servlet showing your best throughput.
4. Test run results (command lines showing metrics, RMQ management windows showing queue size, send/receive rates) for a load balanced servlet showing your best throughput.

## Grading:

1. Server and consumers implementations (15 points)
2. Server design description (5 points) - clarity of description, good design practies used
3. Results (10 points) - single instance tests. Overall throughput, short queue sizes and flat line profile
4. Results (10 points) - load balanced instance tests. Overall throughput (should be greater than no load balancer), short queue sizes and flat line profile

# Deadline: 3/3 11.59pm PST

## Addendum: Multithreading and RabbitMQ

RabbitMQ and multithreading needs a few considerations. Read on ....

The basic abstraction that needs to be operated on by each thread is the channel. This means:

In your servlet (or equivalent if not using a servlet):

1. In the init() method, initialize the connection (this is the socket, so is slow)
2. In the doPost(), create a channel and use that to publish to RabbitMQ. Close it at end of the request.

This should work fine, although the [documentation](https://www.rabbitmq.com/api-guide.html#concurrency) say channels are meant to be long-lived and caution again churn.

So a better solution would be to create a channel pool that shares a bunch of pre-created channels (in .init()) to form a connection pool.

Roll your own is not too hard, but apache commons has a [generic pool implementation](http://commons.apache.org/proper/commons-pool/examples.html) that you could build on.Another approach to implementing a channel pool would be to use a BlockingQueue. Not too tricky ... give it a try!

The [code repo for the book](https://github.com/gortonator/foundations-of-scalable-systems) has examples of these in Chapter 7 that you can use.

On the consumer side, you probably want a multi-threaded consumer that just gets a message and writes to the hash map. In this case you can just create a channel per thread and all should be fine.

There's an excellent write up that describes the complexities of multi-threaded RMQ clients [here](http://moi.vonos.net/bigdata/rabbitmq-threading/)

And [here's](https://github.com/gortonator/bsds-6650/tree/master/code/week-6) some sample code you can work from.

### Starting a program on Linux bootup

Any script in the `/etc/rc.local` file will be run as the last step in booting linux. To start a Java program when you boot the instance, you can put the following command in this file.

    java -jar <pathToYourJar>

Reference: [here](https://unix.stackexchange.com/questions/49626/purpose-and-typical-usage-of-etc-rc-local)

[Back to Course Home Page
