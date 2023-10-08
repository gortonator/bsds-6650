# CS6650 FAQ Outline

## How to succeed in this Course

1. [Prerequisite Knowledge](#Prerequisite-/-Required-Knowledge)
2. [How to do the assignments](#How-to-do-the-assignments?)
3. [Additional Guides](#Additional-Guide-/-Resources)

## AWS

1. [IAM Role for AWS SQS](#aws-sqs-roles)
2. [Load balancer issues](#Unhealthy-AWS-LB-Instances)

## Linux

1. [Starting a program/script on Linux startup](#starting-a-program-on-linux-bootup)
2. Creating Instance Templates for AWS Load Balancing Target Groups
3. [Unhealthy ELB instances - cause and fix](#Unhealthy-AWS-LB-Instances)
4. [Useful Networking Commands](#Useful-Networking-Commands)

## Windows

1. [Powershell equiv of chmod 400](#chmod)

## Servlets and Tomcats

1. [Potential Issues with Lab2](#Lab2-Web-Application-Support-and-Servlet)
2. [Tomcat version](#Things-you-need-to-know-about-Tomcat-Version)
3. [Important: Set your Tomcat Log retention to avoid not enough space in EC2](#Tomcat-Log-Retention-Setting)

## Intellij

1. [ClassNotFoundException in .war built by IntelliJ](#ClassNotFoundException-in-.war-built-by-IntelliJ)
2. [Cannot find 'Web Application; under the "Add Framework support](#IntelliJ-Version-required)
3. [Java Version Error During Compilation or Deployment](#Java-Version-Error-During-Compilation-or-Deployment)
4. [SLF4J Error](#SLF4J-Error)

## RabbitMQ

[Installing RMQ on AWS Linux 2](#Installing-RMQ)

## Kafka

[Additional Guides](#Additional-Guides)

## Swagger issues

Check this post from Spring 2023 class. [here](https://piazza.com/class/lcjrfetosy8581/post/103)

## Other

1. [How to calculate those statistics?](#How-to-calculate-mean,-median,-99th-percentile?)
2. [Is my result good?](#What-are-the-typical-throughput-/-latency-we-looking-at?)

# Answers

## How to Succeed

#### Prerequisite / Required Knowledge

1. Understand how to manage maven dependency in your IDE,  package, and run a war file
2. Understand how to use the tool (for example Postman) to test HTTP requests
3. Understand how to launch an EC2 instance, access and modify the file content of your instance, and manage the security group setting (You will learn some of this by following the labs)

#### How to do the assignments?

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

#### Additional Guide / Resources

1. Lab1 visual guide [here](misc/Lab1_Visual_Guide.pdf)
2. What happened when you launch an EC2 instance with AWS? [check here for quick summary](misc/AWS_EC2_Knowledge.pdf)

## Linux

#### Starting a program on Linux bootup

Any script in the `/etc/rc.local` file will be run as the last step in booting linux. To start a Java program when you boot the instance, you can put the following command in this file.

```
java -jar <pathToYourJar>
```

Reference: [here](https://unix.stackexchange.com/questions/49626/purpose-and-typical-usage-of-etc-rc-local)

##### Useful Networking Commands

```
sysctl -w | grep net.ipv4.tcp_fin_timeout #it's your ports release time default is 60s, you can make it lower like 15s 

sysctl -w net.ipv4.tcp_fin_timeout=15s #set the time to 15s 

sysctl -w net.ipv4.tcp_tw_recycle=1 #turn on the Quickly restore socket resources default is 0 

sysctl -a | grep port_range #check available ports range if the range is lower than your threads, change it 

vim /etc/sysctl.confnet.ipv4.ip_local_port_range = 1024   65535 sysctl -p #make parameters effective`

ulimit -n #check your limit
ulimie -n 66535 #the upper bound of the limit, but when you restart the machine the number will be back, you can write to your /etc/profile
```

## Windows

#### chmod

for windows users use Powershell as follows:

icacls.exe your_key_name.pem /reset

icacls.exe your_key_name.pem /grant:r "$($env:username):(r)"

icacls.exe your_key_name.pem /inheritance:r

thats it! your keys.pem have same restrisctions as you use chmod 400

## AWS

#### AWS SQS roles

To access SQS you have to create a service role in AWS Academy. See docs [here](https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-overview-of-managing-access.html)

This [link](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) offers an alternate method using credentials.

#### Unhealthy AWS LB Instances

This [video](https://www.youtube.com/watch?v=rSoj7PEvWFY&t=395s) is a good source. Basically, you have to check whether 1) the connectivity between your load balancer and your target group is fine; 2) whether your instances in the target group are working fine.

Also, you need your target group to listen on port 8080  using tomcat. This [excellent piazza post](https://piazza.com/class/l7qocxa6gzk5i4/post/170) from wonderful 2022 TA Heng spells it out.

## Servlets and Tomcats

#### Lab2-Web Application Support and Servlet

Check to make sure your intellij IDEA is the ultimate version. 

Still cannot create a new Servlet in Intellij?

Check this answer [Stackoverflow](https://stackoverflow.com/a/72509725)

#### Things you need to know about Tomcat Version

Always installing the same versions of software/applications/packages as mentioned in the labs unless you are confident to know the differences between the versions to install the latest. 

For example, Tomcat version 9 only works with Javax Servlet API version 4.0 but not version 5.0. 
[Apache Reference](https://tomcat.apache.org/whichversion.html), [Java EE vs Jakarta EE](https://www.baeldung.com/java-enterprise-evolution), [Tomcat 10 info](https://www.openlogic.com/blog/apache-tomcat-10)

One of the common causes of bugs is developing software in a version that is not compatible with the software you used in the deployment environment. 

#### Tomcat Log Retention Setting

When installing Tomcat, remember to set the log retention policy of your Tomcat server to a maximum of one day. If you don’t set it, Tomcat will retain all the logs of all requests, which eventually eat up all the storage space of your EC2 instance. To set it. 

Set the following property in your config/server.xml file for the Tomcat

maxDays="1"

[Stackoverflow for detail](https://stackoverflow.com/a/57826692/21508621)

## Intellij

#### ClassNotFoundException in .war built by IntelliJ

In the Project Structure menu, right-click on the (e.g.) `Lab2WebApp` folder (the first one in the Available Elements list). There should be an option to put your  dependencies into the artifact’s WEB-INF/lib directory. Once done, apply the changes and rebuild your artifact. You should (hopefully) not run into the `ClassNotFoundException` anymore, 

#### IntelliJ Version required

You should install Idea Ultimate version (community version has limited support for this feature)

#### Java Version Error During Compilation or Deployment

If you encounter error similar to "source option 7 is no longer supported, use target 8 or higher"

Check the dependencies/compilation target in the POML file. Look for something like

```
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
 </properties>
```

Note for Java version

1. For web application or java program to be deployed into EC2 instance, the compilation target should be equal to the java version installed on EC2
2. For anything run on your local machine (like the client program), you can use any version you want

#### SLF4J Error

This normally wont affect your program. But if you want to know how to solve it. 
[check here](https://stackoverflow.com/a/9919375/21508621)

For maven, that means adding the SLF4J dependencies in your POM.xml, with ${slf4j.version} being the latest version of slf4j

```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
</dependency>
```

## RabbitMQ

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

## Kafka

#### Additional Guides

1. For tutorial, [here](https://www.javatpoint.com/apache-kafka)
2. For Windows user, you can check out the following installation guide [here](https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/)

Note you will be running the BAT (Window Batch File) in the bin \ window directory 

Example: C:\kafka\bin\windows

## Other

#### How to calculate mean, median, 99th percentile?

First way - write your own java program if you have the time. 

Second way - use existing API

You can use the descriptive statistics API  to help calculate the statistics required
[reference](https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html?org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.html)

#### What are the typical throughput / latency we looking at?

For throughput, typical range achieved in past assignments for a single server instance are about few thousand requests handled per second. (2000 - 5/6000). 
If you use multiple servers with load balancer, you can potentially achieve higher. 

If your throughput is below thousand, you probably didnt explore multi threading enough from your client. 

To achieve best throughput of up to 5k/6k, there are a lot of other factors you need to look at. 
One suggestion is try to look for potential bottleneck(s) in your program and think of how to eliminate them. 

The latency figure is directly link to the number of threads you used and the throughput you achieved. 

Typical round trip time between your local machine to AWS resources can range between 10ms - 100ms.
