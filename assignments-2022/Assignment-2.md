# CS6650 Spring 2023 Assignment 2

#### Let's Store Some Data

This is conceptually pretty simple. We're going to implement the doPost() and goGet() methods  and write/read to a database respectively. As always, simple things are not that simple!

#### Client

You only need to make minor changes to your client from assignment 1 (part2). Unless of course, it has unnecessary synchronization - if you have low throughput in assignment 1, then revisit your design and implementation with your instructors. There will be a reason ;)

The required change is to print out the following after the test has finished, along with the other statistics:

- number of successful requests

- number of failed requests

We of course want to see the latter as zero, or maybe a handful at most for each test.

#### Add a Database

You need to deploy, design and implement a database that enables you to:

1. Persist new album information, including the image and JSON-supplied data, during the doPost() method

2. Retrieve album information by primary key in the doGet() method.

You are free to choose any database you like that gives you the nescessary safety gurantees (ie you can't lose writes) and hopefully high performance. Obvious choices include:

- AWS RDS (MySQL or PostGresSQL). You can initially deploy on a free tier instance to keep costs low

- MongoDB: There are managed services you can use but costs/latencies may be prohibitive. Installing MongoDB on its own instance could be a straightforward starting point

- DynamoDB: Easy to access, fast. Cheap? Depending on how you configure it. Be careful and see additional notes below.

- Others - talk to us ...

Bear in mind you have a balanced workload - 50% write and 50% read. This should inform your data model design.

Use the same three workloads for your client as assignment 1, and see what throughput you can achieve?

#### Incorporate Load Balancing

One free tier server for your servlets will probably get pretty busy, so you will want to introduce load balancing.

You can set up [AWS Elastic Load Balancing](https://aws.amazon.com/elasticloadbalancing/features/?nc=sn&loc=2) using either _Application_ or _Network_ load balancers. Enable load balancing with 2 free tier EC2 instances and see what effect this has on your performance. Depending on your database, you may have to allocate connections to each server so that you don't exceed maximum connections.

A tutorial [here](https://docs.aws.amazon.com/elasticloadbalancing/latest/application/application-load-balancer-getting-started.html) should help. Remember to create [AWS templates](https://docs.aws.amazon.com/autoscaling/ec2/userguide/create-launch-template.html) for your instances.

### Tune the System

Run your client against the load balanced servlets and see what effect it has on overall throughput. 

Somewhere you will probably have a bottleneck that you can try to address - use available monotoring tools to find this. Then think about how to remove it, ie:

- database bottleneck - increase capacity (e.g. bigger server, higher throughput configuration)

- Servlet bottleneck - increase capacity (e.g. more load balanced free tier VMs, beefed up instances)

There's a lot of variables here so do your best. See if you can increase the throughput for the 30 Thread group client configuration.

## Submission

1. URL for your code repo

2. A short description of your data model (5 points)

3. Output windows for the 3 client configuration tests run against a single server/DB (5 points)

4. Output windows for the 3 client configuration tests run against a two load balanced servers/DB (15 points)

5. Output window for optimized server configuration for client with 30 Thread Groups. Briefly describe what configuration changes you made and what % throughput improvement you achieved (15 points)

## Deadline : 11/3 11.59pm

## Addendum - DynamoDB

**For those interested in DynamoDB**

If you are interested in using DynamoDB, here are some hopefully useful resources:

1. A series of documentation explaining how to set up confidential before you could make requests to AWS using AWS SDK for Java 2.X.https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html.I have already verified the first two options work well in this [section](https://nam12.safelinks.protection.outlook.com/?url=https%3A%2F%2Fdocs.aws.amazon.com%2Fsdk-for-java%2Flatest%2Fdeveloper-guide%2Fcredentials-chain.html&data=05%7C01%7Ci.gorton%40northeastern.edu%7Cefdb3826568247aa065008db28ec6227%7Ca8eec281aaa34daeac9b9a398b9215e7%7C0%7C0%7C638148767954694150%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=Ul2Fq9eVxt0UogRJ26m%2BbbnrLU8sN9r6LruwHR%2Flny0%3D&reserved=0).The credentials(aws_access_key_id, aws_secret_access_key, aws_session_token) could be found on your learner's lab page by clicking the right top corner "aws details"
2. Examples of interacting with DynamoDB using AWS SDK for Java 2.X.https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.htmlI also found the API reference included in each example really helpful.

DynamoDBPricinghttps://aws.amazon.com/dynamodb/pricing/Pay attention to the difference between “provisioned capacity mode” and“on-demand capacity mode”.As to how to set billing mode in code, check link below https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/dynamodb/model/CreateTableRequest.html#billingMode()Version:1.0StartHTML:0000000105EndHTML:0000042057StartFragment:0000039335EndFragment:0000042017<style></style>
