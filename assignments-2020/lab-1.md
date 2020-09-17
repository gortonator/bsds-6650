# CS6650 Fall 2019  Assignment1

## Lab 1 - Getting started with AWS
### Aims: 
1. Get AWS account up and running - you should have an AWS Educate invitation
1. [Launch a free tier AMI running Amazon Linux](https://aws.amazon.com/getting-started/tutorials/launch-a-virtual-machine/)
1. Install tomcat - see below.
1. [Create an RDS MySQL Instance](https://aws.amazon.com/getting-started/tutorials/create-mysql-db/). You don't have to use MySQL, but the above gives a good introduction to the RDS Service. 

#### Install tomcat8
Make sure you have configured your security group that allows traffic on:

* port 80 and 8080 for the http
* port 22 for ssh
Once your instance has launched, ssh into your instance.
~~~
ssh -i your-amazon.pem ec2-user@instance-address
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
sudo service tomcat8 start
~~~
Tomcat listens on port 8080, so in your browser go to https://{your public IP address}:8080

and with luck you will see the tomcat home page!

If you are running free tier instances, you can leave it running with no cost. If you do stop and restart your instances, just be aware the public IP address will change.

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)