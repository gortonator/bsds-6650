# CS6650 Spring 2023  Assignment 3

##### Lets Go Asynchronous

We're going to augment your solution from assignment 2 with asynchronous request processing to handle a new use case - mainly like and dislikes from users for albums. 

The [Swagger API](https://app.swaggerhub.com/apis/IGORTON/AlbumStore/1.1#) has been modified to include a new endpoint - `/review `- which accepts POST operations that indicate a "like" or "dislike" for an album.  You need to build a servlet to handle this endpoint and store likes and dislikes in a database.

##### Build the Servlet

Like and dislikes are perfect for asynchronous processing. You can accept the request from the client, publish a message to a queue, and then consume messages to 'eventually' update the database. RabbitMQ is the recommended dolution for a queueing platform to use. You can also choose to store likes and dislikes in same table you created in assignment 2, or in a dedicated table. All design choices you have. 

##### Modify the Client

To reduce the new album write load, Just write 100 new albums per thread iteration instead of 1000, and remove the GET request. Then modify you client so that on each thread iteration you:

- POST a new album and image

- POST two likes and one dislike for the album.

##### Performance Testing

As usual, the hard bit. Use your client to load test your servlet(s) (no load balancer) and produce the same results as in assignment 2. Your throughput should be 'roughly' the same as assignment 2.

As your likes/dislikes are handles asynchronously, its possible/likley that your client will finish before all the messages have been processed. This is ok, but we want to be sure your queue(s) are not growing  out of control.   This is unlikely to be your optimal configuration as RMQ struggles with long queue lengths. Hence you need to balance production and consumption rates, such that **production_rate** ≈ **consumption_rate. ** When this condition is met, queue lengths remain small and systems perform predictably and reliably.

You can use the RabbitMQ management console to track the number of messages in the queue, and producers and consumer rates.

The questions you need to explore are:

* How should the database(s) be organized/deployed to handle the higher INSERT/UPDATE load?
* How many queue consumers threads are needed to keep the queue size as close to zero as possible? Less than a 1000 max is a great target, but the main aim is to ensure the queue length doesn't continually grow, then shrink, giving a 'pointy' queue length profile, ie /\\. An increase to a plateau is fine, ie /¯¯¯¯\\. If the plateau is less than around a 1000, you are in great shape!

Submit your work to Canvas Assignment 3 as a pdf document. The document should contain:

1. The URL for your git repo. Create a new folder for your Assignment 3 server code
2. A 1-2 page description of your server design. Include major classes, packages, relationships, how messages get sent/received, etc
3. Test run results (command lines showing metrics, RMQ management windows showing queue size, send/receive rates) showing your best throughput.
   
   

## Grading:

1. Server and consumers implementations (15 points)
2. Server design description (5 points) - clarity of description, good design practies used
3. Results (10 points) - single instance tests. Overall throughput close to assignment 2, short queue sizes and flat line profile
   
   

# Deadline: 11/3 11.59pm PST







Addendum: Multithreading and RabbitMQ

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

Any script in the `/etc/rc.local` file will be run as the last step in booting linux. To start a Java program when you boot the instance, you can put the following command in this file. java -jar <pathToYourJar>

Reference: [here](https://unix.stackexchange.com/questions/49626/purpose-and-typical-usage-of-etc-rc-local)

[Back to Course Home Page

##### 
