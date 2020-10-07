## Lab 6 - Playing with RabbitMQ

RabbitMQ is a massively popular lightweight messaging platform. It has an excellent set of [tutorials](https://www.rabbitmq.com/tutorials/) which are well worth working through.

The [tutorial on RPC](https://www.rabbitmq.com/tutorials/tutorial-six-java.html) is probaly the most interesting. In this lab:

1. Look at the prerequisites and install RabbitMQ on your machine. Work through [tutorial 1](https://www.rabbitmq.com/tutorials/tutorial-one-java.html) to make sure you have the Java client and environment set up correctly. 
1. Now work through tutorial 6. The code is reasonably complex so run it and make sure you understand what is happening.
1. The tutorial raises some interesting issues at the end, listed below. How would you modify teh tutorial exampl to make it more robust?
  - How should the client react if there are no servers running?
  - Should a client have some kind of timeout for the RPC?
  - If the server malfunctions and raises an exception, should it be forwarded to the client?
  - Protecting against invalid incoming messages (eg checking bounds, type) before processing.
  
  [Back to Course Home Page](https://gortonator.github.io/bsds-6650/)