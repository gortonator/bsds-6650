# CS6650 FAQ
## How to succeed in this Course
1. [Prerequisite Knowledge](#Prerequisite-/-Required Knowledge)


## AWS

1. [IAM Role for AWS SQS](#aws-sqs-roles)
2. [Loan balancer issues](#Unhealthy-AWS-LB-Instances)

## Linux

1. [Starting a program/script on Linux startup](#starting-a-program-on-linux-bootup)
2. Creating Instance Templates for AWS Load Balancing Target Groups
3. [Unhealthy ELB instances - cause and fix](#Unhealthy-AWS-LB-Instances)

## Servlets and Tomcats
### Lab2 - Web Application Support and Servlet
Check to make sure your intellij IDEA is the ultimate version. 

Still cannot create a new Servlet in Intellij?

Check this answer [Stackoverflow](https://stackoverflow.com/a/72509725)

### Things you need to know about Tomcat
Always installing the same versions of software/applications/packages as mentioned in the labs unless you are confident to know the differences between the versions to install the latest. 

For example, Tomcat version 9 only works with Javax Servlet API version 4.0 but not version 5.0. 
[Apache Reference](https://tomcat.apache.org/whichversion.html), [Java EE vs Jakarta EE](https://www.baeldung.com/java-enterprise-evolution), [Tomcat 10 info](https://www.openlogic.com/blog/apache-tomcat-10)

One of the common causes of bugs is developing software in a version that is not compatible with the software you used in the deployment environment. 

### Not enough space in your EC2 instance after working on the project for sometime?
When installing Tomcat, remember to set the log retention policy of your Tomcat server to a maximum of one day. If you don’t set it, Tomcat will retain all the logs of all requests, which eventually eat up all the storage space of your EC2 instance. To set it. 

Set the following property in your config/server.xml file for the Tomcat

maxDays="1"

[Stackoverflow for detail](https://stackoverflow.com/a/57826692/21508621)

## Intellij

[ClassNotFoundException in .war built by IntelliJ](#ClassNotFoundException-in-.war-built-by-IntelliJ)
[Cannot find 'Web Application; under the "Add Framework support](#IntelliJ Version required)

## RabbitMQ

[Installing RMQ on AWS Linux 2](#Installing-RMQ)

Swagger

# Answers
## How to Succeed
### Prerequisite / Required Knowledge
1. Understand how to manage maven dependency in your IDE,  package, and run a war file
2. Understand how to use the tool (for example Postman) to test HTTP requests
3. Understand how to launch an EC2 instance, access and modify the file content of your instance, and manage the security group setting (You will learn some of this by following the labs)

### What is actually happening when you proceed with the assignments?
1. You code your main java programs in Integrated Development Environment (IDE like IntelliJ) on your local machine.
2. For server program, that usually means creating java servlet file and application. 
3. You integrated it with some other services like Tomcat (for hosting your webapp), message service (Example: RabbitMQ), and database service.
4. You test the program on your local machine to make sure they are running fine without bugs. Recommended testing tools for HTTP requests: https://www.postman.com/
5. You Convert your program to the required file (war or jar)
6. You set up the server machines (EC2 instances) provided by Amazon Web Services (AWS) via AWS Learner Lab, AWS Management Console, and SSH into your EC2 instances.
7. Your EC2 instances will normally launch with OS of Amazon Linux or Ubuntu (because they are free).Thus you typically need to install programs via SSH and command prompt.
8. Depending on your design choices, there could be additional AWS services required to set up, such as ALB, SQS, DynamoDB and so on. 
9. You copied your program in step 5 and deployed them onto your server. 
10. You run your tests from your local machine to send HTTP requests to the server on the EC2 instance(s), either through client program or Apache JMeter as per assignments requirement.

Sound easy/ confused? Here are some more guides
### Additional Guide / Resources
1. Lab1 visual guide [here](misc/Lab1_Visual_Guide.pdf)
2. What happened when you launch an EC2 instance with AWS? [check here for quick summary](misc/AWS_EC2_Knowledge.pdf)

## Linux

#### Starting a program on Linux bootup

Any script in the `/etc/rc.local` file will be run as the last step in booting linux. To start a Java program when you boot the instance, you can put the following command in this file.

```
java -jar <pathToYourJar>
```

Reference: [here](https://unix.stackexchange.com/questions/49626/purpose-and-typical-usage-of-etc-rc-local)

## AWS

#### AWS SQS roles

To access SQS you have to create a service role in AWS Academy. See docs [here](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-overview-of-managing-access.html)

This [link](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) offers an alternate method using credentials.

#### Unhealthy AWS LB Instances

This [video](https://www.youtube.com/watch?v=rSoj7PEvWFY&t=395s) is a good source. Basically, you have to check whether 1) the connectivity between your load balancer and your target group is fine; 2) whether your instances in the target group are working fine.

Also, you need your target group to listen on port 8080  using tomcat. This [excellent piazza post](https://piazza.com/class/l7qocxa6gzk5i4/post/170) from wonderful 2022 TA Heng spells it out.

## Intellij

#### ClassNotFoundException in .war built by IntelliJ

In the Project Structure menu, right-click on the (e.g.) `Lab2WebApp` folder (the first one in the Available Elements list). There should be an option to put your  dependencies into the artifact’s WEB-INF/lib directory. Once done, apply the changes and rebuild your artifact. You should (hopefully) not run into the `ClassNotFoundException` anymore, 

#### IntelliJ Version required

You should install Idea Ultimate version (community version has limited support for this feature)

# RabbitMQ

#### Installing RMQ

sudo yum install epel-release  
sudo amazon-linux-extras install epel  
sudo yum install erlang  
sudo yum install rabbitmq-server

sudo rabbitmq-plugins list  
sudo rabbitmq-plugins enable rabbitmq_management  
sudo systemctl enable rabbitmq-server  
sudo systemctl start rabbitmq-server  
sudo systemctl stop rabbitmq-server

whereis rabbitmq  
sudo chown -R ec2-user: /var/log/rabbitmq

http://(YourDNS):15672/

# Other
## How to calculate mean, median, 99th percentile?
First way - write your own java program. 

Second way - use existing API

You can use the descriptive statistics API  to help calculate the statistics required
[reference](https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html?org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.html)


