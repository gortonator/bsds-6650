# Project Ideas

Assuming Group size of 3. You can scale down the effort for smaller groups. Use these as inspiration and feel free to be creative and explore aspects of scalability that you find interesting.


## Server Diversity
1. Reimplement your Java servlet on AWS using two other Java frameworks such as  SpringBoot, Spring,  [Google Guice](http://www.aberger.at/en/blog/design/2016/11/16/dependency-injection-guice.html). With an identical DAO layer, compare the performance of the 3 implementations.
1. Reimplement your GAE server using two different languages, eg Python, Go,  Node, and compare the performance of the 3 implementations.
1. Build two versions of the server using AWS Lambda, using a different language for each. Compare the performance of the two with you GAE server.

## Communications Layer Diversity
1. Reimplement your client/server using two of [Websockets](https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/HomeWebsocket/WebsocketHome.html), [Thrift](https://stackoverflow.com/questions/9732381/why-thrift-why-not-http-rpcjsongzip) over HTTP/JSON or even good old sockets, and compare the performance of the two protocols with REST/HTTP
1. Redesign your client/server to operate with a Kafka/RabbitMQ/etc endpoint exposed over HTTP for writing new lift records. Compare the performance of the two with you REST/HTTP server for writing.

## Database Diversity
1. Reimplement your server DAO layer to read/write to two different databases such as Mongodb, neo4j, dynamodb, etc. Compare the performance of the two implementations with your original version

## Redesign/Extend Application
1. Refactor your application to be based on several microservices. Incorporate circuit breakers and bulkheads from two different libraries to provide a resilient, easily extensible and modifiable application architecture. Comapre the libraries in terms of ease of use, range of features, usefulness, etc. 
1. Redesign your application to be based on streaming. Data from clients would be POSTED to a streaming server (eg Kafka/Storm/etc) and as well as writing to the database, it would calculate  statistics like ‘number of lift rides in each hour’, “number of runs by skier X”,” number of rides on lift N”, which can be exposed on new endpoints
1. Incorporate analytics using Hadoop/Spark. This will require you to generate multiple days of lift ride data for a resort/season (easy with your client). Then design a mechanism to extract data from your DB into HDFS/S3/?? so that you can run Hadoop/spark jobs on it. For example, You might want to answer queries like “which N skiers have the most vertical for the season?”, “which lifts are ridden most during the season?”. “what is the average number of skier lift rides between noon and 1pm (or any given time interval)?”
