# Java Multithreaded Exercises

## Counter

Write a Java multithreaded programs that 

1. takes a time stamp, and start N threads, default 1K
1. each thread increments a shared synchronized counter 10 times and terminates
1. when all threads are completed, the main thread takes a time stamp and prints out the counter value and the duration it takes to run the program

Run the program with a variable number of threads (e.g.1,10k etc) and see if you can observe any relationship between number of threads and total run time?


## Collections (1)

Write a Java program that uses a single thread to add a lot of elements (100k?) to a:

* Vector
* ArrayList

Time how long each test takes to quantify the overheads of synchronization.

## Collections (2)
Write a Java program that uses a single thread to add a lot of elements (100k?) to a:

* HashTable
* HashMap
* ConcurrentHashMap

1. Time how long each test takes to quantify the overheads of synchronization.
2. Make your program mulththreaded with e.g 100 threads and and again compare performance. 

You will have to make access to the HashMap threadsafe using Collections.synchronizedMap.

## Investigating the costs of file access and context switching

You aim is to write a multithreaded program that writes information to a test file as fast as possible.

Start by creating a Java program that opens a text file for writing.

Then create 500 threads and in each thread generate 1000 strings of the format:
"timestamp, Thread-id, N"

where:
1. _timestamp_ is the output of calling _System.currentTimeMillis();_
1. _Thread-id_ is result of calling _Thread.currentThread().getId();_
1. _N_ is the loop iteration, i.e. between 0 and 999

Your aim to to store all these generated strings from all threads into the same file as quickly as possible. 
The order they are written to the file is initially unimportant. 

Time how long the program takes by taking a timestamp immediately before creating any threads and another when all threads are completed and the file is written and closed.

Approaches you can experiment with are along the lines of:
1. write every string to the file immediately after it is generated in the loop in each thread
1. write _all_ the strings from one thread after they are generated and just before a thread terminates
1. Store all the strings from all threads in a shared collection, and write this to a file from your main() thread after all threads are completed
1. others?

Implement two or more of these approaches and compare the wall time for the program.

What insights do you take away from this exercise?

Extensions:
1. Test your solutions with threads that generate 2000 strings rather than 1000. What about 5000? 10000?
1. Can you design a solution in which only one thread writes to the file _while_ the threads are generating the strings.
1. How would you modify any of your solutions to ensure the data is written to the file in ascending timestamp order?








[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)