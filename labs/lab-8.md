## Lab 8 - Circuit Breaker Pattern

The circuit breaker can be used in clients to reduce strain on a server that is under heavy load, and to potentially enable a service to provide more stable response times.

If we think about this in the context of our assignments, could we use a circuit breaker in 
1. the client, which could set a latency threshold and back off if this is exceeded?
1. the server, when writing to RabbitMQ?

Take a look at your client code and circuit breaker libraries. 

The Apache library is probably the easiest to get started with and [these examples](https://commons.apache.org/proper/commons-lang/javadocs/api-3.9/org/apache/commons/lang3/concurrent/EventCountCircuitBreaker.html) might be inspiring, but choose any you'd like to learn.

Can you add a circuit breaker in your assignment to obtain better performance as the load grows?

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)

