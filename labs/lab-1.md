# CS6650 Fall 2020  

This lab is designed to guide you to create an EC2 instance on AWS running AWS Linux.

Lab 1a works for AWS Linux version 1.0. Lab 1b should be followed if you create an AWS Linux version 2 instance, which is now the supported version.

## Lab 1a - Getting started with AWS Linux 1
### Aims: 
1. Get AWS account up and running - you should have an AWS Educate invitation
1. Follow the instructions in the next step to create an EC2 instance. The instructions below assume you have chosed an AWS Linux (v1) image. 
1. [Launch a free tier AMI running Amazon Linux](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html)
1. Install tomcat - see below.
1. [Create an RDS MySQL Instance](https://aws.amazon.com/getting-started/tutorials/create-mysql-db/). You don't have to use MySQL, but the above gives a good introduction to the RDS Service. 

#### Install tomcat8
Make sure you have configured your security group that allows traffic on:

* port 80 and 8080 for the http
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

## Lab 1b - Getting started with AWS Linux 2
### Aims: 
1. Get AWS account up and running - you should have an AWS Educate invitation
1. Follow the instructions in the next step to create an EC2 instance. The instructions below assume you have chosed an AWS Linux (v1) image. 
1. [Launch a free tier AMI running Amazon Linux](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html)
1. Install tomcat - [Follow the instruction for the first 3 steps](https://techviewleo.com/install-tomcat-on-amazon-linux/)
1. Tomcat listens on port 8080, so in your browser go to http://{your public IP address}:8080 and you should see the Tomcat homepage
1. [Create an RDS MySQL Instance](https://aws.amazon.com/getting-started/tutorials/create-mysql-db/). You don't have to use MySQL, but the above gives a good introduction to the RDS Service. 


[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
