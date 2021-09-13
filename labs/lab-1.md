# CS6650 Lab 1  

This lab is designed to guide you to create an EC2 instance on AWS running AWS Linux.

AWS Linux 2 is the version to use. Deprecated instructions for AWS Linx 1 are below.

## Lab 1 - Getting started with AWS Linux 2
### Aims: 
* Get AWS account up and running - you should have an AWS Educate invitation
* [Launch a free tier AMI running Amazon Linux 2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html)
* Make sure you have configured your security group that allows traffic on:
** port 80 for http and 8080 as a Custom TCP Rule (Tomcat listens on this port by default)
** port 22 for ssh
* Install tomcat - [Follow the instruction for the first 3 steps](https://techviewleo.com/install-tomcat-on-amazon-linux/)
* Tomcat listens on port 8080, so in your browser go to http://{your public IP address}:8080 and you should see the Tomcat homepage
 

## Notes on EC2 Charging
Check out [this info](https://aws.amazon.com/about-aws/whats-new/2017/06/amazon-rds-supports-stopping-and-starting-of-database-instances/) on costs of RDS and automatic restarts. We will start using a database in Assignment 2 so feel free to terminate until then.
If you stop/start your EC2 instance, the public IP address will change. The extract below is from [stackoverlow](https://stackoverflow.com/questions/55414302/an-ip-address-of-ec2-instance-gets-changed-after-the-restart#:~:text=5%20Answers&text=Actually%2C%20When%20you%20stop%2Fstart,used%20by%20other%20EC2%20instances).
Actually, When you stop/start your instance, the IP address will change. If you reboot the instance, it will keep the same IP addresses. Unfortunately, it is not possible for us to reassign the address to your instance as that address would have been released back into the pool used by other EC2 instances.

If you want to avoid this issue in the future, depending on your needs:

* If you only need a fixed public IP address, you can assign an Elastic IP address to your instance.
* If you need both public and private IP addresses to remain the same throughout the lifetime of the instance, you can launch your instance in VPC instead. The private IP address assigned to an instance in VPC remains with the instance through to termination.
To learn more, see the aws documentation to assign elastic ip.

And from [here](https://www.parkmycloud.com/ec2-stop-vs-terminate/)

You are not billed for stopped EC2 instances. 
* This makes stopping instances when not in use a valuable cost-saving strategy.However, you are billed for some attached resources regardless of the instance state – such as Amazon EBS volumes and Elastic IP addresses. 
* You are not charged for data transfer fees. 
* When an instance is stopped and then started, a new billing period begins for a minimum of one minute.


## Lab 1 Deprecated - Getting started with AWS Linux 1
AT some stage you won't be able to create an AWS Linux 1 instance, so teh below will be moot.
### Aims: 
1. Get AWS account up and running - you should have an AWS Educate invitation
1. Follow the instructions in the next step to create an EC2 instance. The instructions below assume you have chosed an AWS Linux (v1) image. 
1. [Launch a free tier AMI running Amazon Linux](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html)
1. Install tomcat - see below.
1. [Create an RDS MySQL Instance](https://aws.amazon.com/getting-started/tutorials/create-mysql-db/). You don't have to use MySQL, but the above gives a good introduction to the RDS Service. 

#### Install tomcat8
Make sure you have configured your security group that allows traffic on:

* port 80 for http and 8080 as a Custom TCP Rule (Tomcat listens on this port by default)
* port 22 for ssh
Once your instance has launched, ssh into your instance.
~~~
ssh -i your-amazon.pem ec2-user@instance-address-public-IP
~~~
Install Java 8
Check the version of java installed 
~~~
java -version
~~~
If its not Java 8, install as below
~~~
sudo yum install java-1.8.0
sudo yum remove java-1.7.0-openjdk
~~~
Now install Tomcat 8
~~~
sudo yum install tomcat8 tomcat8-webapps
~~~
You can check the installation locations using
~~~
rpm -ql tomcat
~~~
Now start tomcat
~~~
sudo service tomcat8 start
~~~
Tomcat listens on port 8080, so in your browser go to 
* http://{your public IP address}:8080

and with luck you will see the tomcat home page!

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
