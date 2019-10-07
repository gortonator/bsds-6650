A simple experment with Lamport clocks.

-multiple threads exchange messages through a shared bounded buffer, and execute a fixed number of iterations
-Threads alternate between internal events and sending/receiving messages to increment the logical clock
-Random sleep periods ensure we get very non-deterministic behaviour
-Threads can't consume message that they sent.

Threads complete and print out the order of events thay see locally. 
Currently this is not synchronized so could interleave - one to fix :)
