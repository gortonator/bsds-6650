# CS6650 FAQ

## AWS

1. [IAM Role for AWS SQS](#aws-sqs-roles)
1. [Loan balancer issues](#Unhealthy-AWS-LB-Instances)



## Linux

1. [Starting a program/script on Linux startup](#starting-a-program-on-linux-bootup)
2. Creating Instance Templates for AWS Load Balancing Target Groups
2. [Unhealthy ELB instances - cause and fix](#Unhealthy-AWS-LB-Instances)

## Servlets



## Intellij

[ClassNotFoundException in .war built by IntelliJ](#ClassNotFoundException-in-.war-built-by-IntelliJ)



## RabbitMQ





# Answers

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