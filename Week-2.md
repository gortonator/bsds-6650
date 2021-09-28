# Introduction to Concurrency

## Learning Objectives

1. Describe the difference between a thread and a process
1. Describe the two major problems that occur in multi-threaded systems
1. Write programs using threads in Java
1. Write programs using thread safe collections, queues and executors
1. Lab: Understand and run a simple servlet in tomcat

## Module Contents

1. [Why threads? Simple threads in Java](https://youtu.be/iMlkyQB5zr0) 
1. [Problems with threading and Synchronization primitives](https://youtu.be/89B_stQf9no)
1. [Thread coordination and Thread states](https://youtu.be/kx9PNbumHdc)
1. [The Producer-Consumer problem and Thread Pools](https://youtu.be/uk0ICzb7Vmc)
1. [Thread-safe collections](https://youtu.be/0P19ziFMkbI)


## Code Examples
Can be found [here](https://github.com/gortonator/bsds-6650/tree/master/code/week-2)

## Reading
Mandatory:
- Chapter 4 of Concurrency and Scalability for Distributed Systems 

Optional:
* Java Concurrency in Practice 1st Edition, by Brian Goetz, Tim Peierls, Joshua Bloch, Joseph Bowbeer, David Holmes, Doug Lea
** Chapters 1-8 are relevant to the assignments, so use these as your main sourse of information
* [Shooting yourself in the foot with Random number generators](https://plumbr.io/blog/locked-threads/shooting-yourself-in-the-foot-with-random-number-generators)
* [Interesting article on thread-safe Java collections](https://www.codejava.net/java-core/collections/understanding-collections-and-thread-safety-in-java)

## Java Multithreading Lab
### Aim
Write some simple Java multithreaded programs using basic collections.Instructions are [here](https://gortonator.github.io/bsds-6650/labs/JavaMTLab)

## Lab 2 - Simple Server with Java Servlet
### Aims: 
1. Build a Java Servlet to process two of the APIs in Assignment 1
1. Echo the API parameter values back to the client
1. Learn simple testing with POSTMAN

Follow the instructions [here](https://gortonator.github.io/bsds-6650/labs/lab-2)


[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
