# Week 9: Strong Consistency and Distributed Databases

## Learning Objectives
1. Explain how 2 phase commit works and how it handles failures
1. Explain the implications of the FLP result
1. Describe the Raft consensus algorithm
1. Explain causal consistency in Neo4j

## Mandatory Lecture Materials
We'll use lecture materials from last year's class as these haven't changed in content - just the order (they were week 5 last year)

1. [Strong Consistency and Transactions](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=060abd7e-7f6c-4f03-a6bf-ab9100ddae42)
1. [Raft in Neo4j](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=489ec07a-1ea0-4378-bcb4-ab9100ddadac)

## Mandatory Reading
1. Chapter 12 of course book
1. Chapter 9, Designing Data-Intensive Applications, Martin Kleppmann, O'Reilly Publishing (available online at Snell library)

## Optional Reading
1. Chapter 7, Designing Data-Intensive Applications, Martin Kleppmann, O'Reilly Publishing (available online at Snell library)
1. [Raft versus Paxos from MIT Lecture Series](https://www.youtube.com/watch?v=9QGGB5sCr1g)
1. [Raft Features in MongoDB](https://www.youtube.com/watch?v=jCk0FCbqCz0)

## Labs
**Class Exercises:** 
Work through the Raft explanation [here](http://thesecretlivesofdata.com/raft/) and make sure you understand how the protocol works.

Install Redis on EC2. Install instructions on AWS Linux are [here](https://shawn-shi.medium.com/how-to-install-redis-on-ec2-server-for-fast-in-memory-database-f30c3ef8c35e) or if Medium access is painful [here](https://www.phaedrasolutions.com/blog/setup-redis-on-aws).

Choose and test a Redis Java client from [here](https://redis.io/clients#java). [Jedis](https://github.com/redis/jedis) is popular,









[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
