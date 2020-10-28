## Lab 9 - Logical Clocks

## A simple experment with Lamport clocks.

Copy, build and run the code from [Week 4](https://github.com/gortonator/bsds-6650/tree/master/code/week-4)

Multiple threads - LamportClient.java - exchange messages through a shared bounded buffer, and execute a fixed number of iterations

These threads alternate between internal events and sending/receiving messages to increment the logical clock

Random sleep periods ensure we get very non-deterministic behaviour

Threads can't consume message that they sent.

Threads complete and print out the order of events thay see locally. Currently this is not synchronized so the output interleaves, making it impossible to interpret. 

Your tasks:
1. Ensure the thread output does not interleave
2. When you can make sense of the output, see if it helps you understand the Lamport algorithm.
3. Have a read about how [Version Vectors work](https://en.wikipedia.org/wiki/Version_vector). Can you extend this example to implement the version vector algorithm?

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
