# Java Multithreaded Exercises

## Counter

Write a Java multithreaded programs that 
1. take a time stamp, and start N threads, default 1K
1. each thread updates a shared synchronized counter and terminates
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
2. Make your program mulththreaded with e.g 100 threads and and again compare performance. You will have to make access to the HashMap threadsafe using Collections.synchronizedMap.

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)