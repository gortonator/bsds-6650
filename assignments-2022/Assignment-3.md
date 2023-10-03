# CS6650 Spring 2023  Assignment 3

COMING SOON



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


