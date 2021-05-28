# CS6650 Summer 2021  Assignment 2

## Building the Server
This assignment builds on assignment 1. Your client won't change (unless it has a bug in it!). 
Your will implement processing logic in your server and post the result to a queue for subsequent processing in assignment 3.

## Step 1 - Implement the Server
You need to implemet the following business logic in your server:

1. Split the line of text into individual words and generate a tuple of {word, count} for every word in the lines
1. Post the results from each line to a queue
1. Return the number of unique words in the line to the client

For example if the line contains "Hello Polly want a banana" you would generate 5 tuples:
1. {Hello, 1}
1. {Polly, 1}
1. {want, 1}
1. {a, 1}
1. {banana, 1}

For simplicity, treat symbols like '*' as words too, and URL. Basically just split the line and everything is a word. 

Choose your own queue technology. RabbitMQ is an obvious one, AWS SQS another. Make sure you deploy RabbitMQ on its own EC2 instance.
Your aim is to keep response times as low as possible. One free tier server will probably get pretty busy, so you may want to introdue load balancing or scale up your server (it'll cost you!).

You can set up [AWS Elastic Load Balancing](https://aws.amazon.com/elasticloadbalancing/features/?nc=sn&loc=2) using either _Application_ or _Network_ load balancers. 
Enable load balancing with e.g. 4 free tier EC2 instances and see what effect this has on your performance.

## Step 2 - Implement a Consumer
Implememt a plain old Java program to pull messages off the queue. For each unique word received, create an entry in hash map of similar structure with the word as the key and count as the value.
When a word is consumed that is in the hash map, simply increment the count.

Your aim is to consume messages, ideally, as quickly as they are produced. This means your consumer will need to be multithreaded and your hash map thread safe.

## Load Testing
 
Your aim here is to find the 'best' application configuration in terms of responsiveness to the client and managing queue size. 
 
The questions you need to explore are:
* Do I need to scale out with load balancing for my server? Or can my system work with 1 free-tier (or  upgraded) server
* How many queue consumers threads do I need to keep the queue size as close to zero as possible. 

Just to make things fun, [use this one for testing](https://github.com/gortonator/bsds-6650/blob/master/assignments-2021/bsds-summer-2021-testdata-assignment2.txt)
It's a _little_ big bigger - might be wise to test assignment 1 with this before getting adventurous ;)

You can use the RabbitMQ management console to track the number of messages in the queue, and producers and consumer rates.
 
## Submission Requirements
Submit your work to Canvas Assignment 2 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 2 server code
1. A 1-2 page description of your server design. Include major classes, packages, relationships, how messages get sent/received, etc
1. Test runs (command lines, RMQ management windows showing queue size, send/receive rates) for 64, 128 and 256 client threads
1. Bonus Points - Results for a test with 512 client threads

## Grading:
1. Server and consumer  implementations working (15 points)
1. Server design description (5 points) - clarity of description, good design practies used
1. Results (20 points) - throughput, persistent/non-persistent queues, different instances, basically describe and analyze what you did
1. Results of a successful run with 512 client threads (5 points) 

# Deadline: 6/16 11.59pm PST 

## Addendum: Multithreading and RabbitMQ

RabbitMQ and multithreading needs a few considerations. Read on ....

The basic abstraction that needs to be operated on by each thread is the channel. This means:

In your servlet (or equivalent if not using a servlet):

1. In the init() method, initialize the connection (this is the socket, so is slow)
1. In the dopost(), create a channel and use that to publish to RabbitMQ. Close it at end of the request.

This should work fine, although the [documentation](https://www.rabbitmq.com/api-guide.html#concurrency) say channels are meant to be long-lived and caution again churn. 

So a better solution would be to create a channel pool that shares a bunch of pre-created channels (in .init()) to form a connection pool. 

Roll your own is not too hard, but apache commons has a [generic pool implementation](http://commons.apache.org/proper/commons-pool/examples.html) that you could build on.

On the consumer side, you probably want a multi-threaded consumer that just gets a message and writes to the hash map. In this case you can just create a channel per thread and all should be fine. 

There's an excellent write up that describes the complexities of multi-threaded RMQ clients [here](http://moi.vonos.net/bigdata/rabbitmq-threading/)

And [here's](https://github.com/gortonator/bsds-6650/tree/master/code/week-6) some sample code you can work from. 

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)

