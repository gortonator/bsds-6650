# CS6650 Lab 1  

This lab is designed to guide you to create an EC2 instance on AWS running AWS Linux.

AWS Linux 2 is the version to use. You may choose another Linux instance but will be on your own.

## Lab 1 - Getting started with AWS Linux 2
### Aims: 
* Get AWS account up and running - you should have an AWS Academy invitation
* Sign into the AWS Academy Learner Lab. Hit the 'Start' button for any of the labs, and watch the alien-like V symbol spin for a long time. When it finished the 'AWS' logo on the left should be green, Hit this and it will throw you into an AWS Console Window. From that window you should be able to follow the instructions in the next step.
* [Launch a free tier AMI running Amazon Linux 2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html) on us-west2 (it should be available)
* You must configure access to your virtual machine uisng AWS Security Groups. [This is a good overview](https://www.javatpoint.com/aws-security-group) if you rae unfamiliar
* Make sure you have configured your security group that allows traffic on:
  - port 80 for http and 8080 as a Custom TCP Rule (Tomcat listens on this port by default)
    port 22 for ssh. 
  - Make these ports accessible from "My IP" for when you are working from home and the University subnet for when you are working on campus
  - Under no circumstances open any port to everywhere. You will get hacked and lose your account.
* Install tomcat - [Follow the instruction for the first 5 steps](https://techviewleo.com/install-tomcat-on-amazon-linux/)
  - ignore the instructions to configure the firewall service atthe end of Step 3.
* Tomcat listens on port 8080, so in your browser go to http://{your public IP address}:8080 and you should see the Tomcat homepage

Once you get this far, life looks pretty good. First mission accomplished! In 3 weeks you'l; be able to do all this in your sleep. 

Some notes based on first experience with the Learner Lab:

- Download a new key pair when you launch your first instance. You can then use this for all subsequent instances you launch
- a .cer file seems to be teh same as a .pem file, so you can use that in ssh commands


## Notes on AWS Academy Learner Labs and Charging
We will be using AWS Academy Learner Labs for this course.

*AWS Academy Learner Lab - Foundation Services* provides a long-running sandbox environment for ad hoc exploration of AWS services. Within this class, you will have access *to **a restricted set of AWS services***. Not all AWS documentation walk-through or sample labs that operate in an AWS Production account will work in the sandbox environment. You will retain access to the AWS resources set up in this environment for the duration of this course. You are limited in budget ($100), so you should exercise caution to prevent charges that will deplete your budget too quickly. If you exceed your budget, you will lose access to your environment and lose all of your work.

Each session lasts for 4 hours by default, although you can extend a session to run longer by pressing the start button to reset your session timer. At the end of each session, any resources you created will persist. However, AWS automatically shuts EC2 instances down. Other resources, such as RDS instances, keep running. Keep in mind that AWS  does not stop some AWS features, so they can still incur charges between sessions. For example, an Elastic Load Balancer or a NAT. You may wish to delete those types of resources and recreate them as needed to test your work during a session. You will have access to this environment for the duration of the class they enrolled you in. When the class ends, your access to the learner lab will also end.

When you stop/start your EC2 instance, the public IP address will change. The extract below is from [stackoverlow](https://stackoverflow.com/questions/55414302/an-ip-address-of-ec2-instance-gets-changed-after-the-restart#:~:text=5%20Answers&text=Actually%2C%20When%20you%20stop%2Fstart,used%20by%20other%20EC2%20instances).
Actually, When you stop/start your instance, the IP address will change. If you reboot the instance, it will keep the same IP addresses. Unfortunately, it is not possible for us to reassign the address to your instance as that address would have been released back into the pool used by other EC2 instances.

If you want to avoid this issue in the future, depending on your needs:

* If you only need a fixed public IP address, you can assign an Elastic IP address to your instance.
* If you need both public and private IP addresses to remain the same throughout the lifetime of the instance, you can launch your instance in a VPC instead. The private IP address assigned to an instance in a VPC remains with the instance through to termination.
To learn more, see the aws documentation to assign elastic ip.

And from [here](https://www.parkmycloud.com/ec2-stop-vs-terminate/)

You are not billed for stopped EC2 instances. 
* This makes stopping instances when not in use a valuable cost-saving strategy.However, you are billed for some attached resources regardless of the instance state – such as Amazon EBS volumes and Elastic IP addresses. 
* You are not charged for data transfer fees. 
* When an instance is stopped and then started, a new billing period begins for a minimum of one minute.


#### Install tomcat8 (DEPRECATED INSTRUCTIONS)
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
