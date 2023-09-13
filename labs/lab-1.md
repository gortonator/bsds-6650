# CS6650 Lab 1  

This lab is designed to guide you to create an EC2 instance on AWS running AWS Linux, and deploy your webserver built using Go-Gin framework onto it. 

AWS Linux 2023 or AWS Linux 2 is the version to use. You may choose another Linux instance but will be on your own.

## Prerequisite
Completed the two tutorials in Lab 0. 

https://go.dev/doc/tutorial/getting-started

https://go.dev/doc/tutorial/web-service-gin

Note for second tutorial. change the address of your server in main function to remove the localhost reference as below. 
```
Original : router.Run("localhost:8080")
Change to: router.Run(":8080")
```
This is to enable the server to run on EC2. 

## Lab 1 - Getting started with AWS EC2 Instance
### Aims: Cross Compile Your Go-Gin code 
* Basically we want to cross compile the Go-Gin codes coded on Your local machine into binary executable file that you can run on EC2 directly.
  - What you need is to specify the target Operating System (OS) and architecture when compiling and build your go application. 

* After you build and test your code. open a bash terminal, navigate to the main directory of your Go-Gin code file (where your main.go reside), 
  - For window, you will need to open a git-bash terminal from VS Studio Code. Check [here](https://code.visualstudio.com/docs/sourcecontrol/intro-to-git#_git-bash-on-windows)
 
* Run the following command to cross-compile your code
  ```
  GOOS=linux GOARCH=amd64 go build -o <your-filename> main.go
  ```
  - Here, GOOS set the target OS to linux,
  - GOARCH set the target architecture to amd64. (For AWS Linux, it will either be amd64 or arm64, try the other if the first didnt work)
  - For list of available OS and GOARCH, check [here](https://go.dev/doc/install/source#environment)
  - go build command build the executable file with code resides on main.go (the file name you write your code)
  - -o <your-filename> specify the output name of the executable binaries file.
  - Useful reference for compilation tutorial [here](https://go.dev/doc/tutorial/compile-install), command options [here](https://pkg.go.dev/cmd/go)

### Aims: Set Up Your EC2 Instance
* Get AWS account up and running - you should have an AWS Academy invitation

* Sign into the AWS Academy Learner Lab. Hit the 'Start' button for any of the labs, and watch the alien-like V symbol spin for a long time. When it finished the 'AWS' logo on the left should be green, Hit this and it will throw you into an AWS Console Window. From that window you should be able to follow the instructions in the next step. For visual guide reference, you can check [here](../misc/Lab1_Visual_Guide.pdf)

* [Launch a free tier AMI running Amazon Linux 2023 or Amazon Linux 2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EC2_GetStarted.html) on us-west2 (it should be available)

* You must configure access to your virtual machine uisng AWS Security Groups. [This is a good overview](https://www.javatpoint.com/aws-security-group) if you are unfamiliar

* Make sure you have configured your security group that allows traffic on:
  - port 80 for http and 8080 as a Custom TCP Rule (Same port as your Go-Gin server)
    port 22 for ssh. 
    
  - Make port 22 accessible from  "My IP" for when you are working from home. On campus you will need to redo this rule each time as your allocated IP address might change

  - For port 80 and 8080 In Seattle, AWS would see the following incoming IP CIDRs, so create Custom TCP Rules for the following addresses. These rules should always work on campus:

    63.208.141.34/29

    63.208.141.234/29

  - Alternatively, make port 80 and 8080 accessible from My IP as well if you testing from home. As your ISPs may give you different IP address from CIDR blocks above. 
  - Under no circumstances open any port to everywhere. You will get hacked and lose your account.

* ssh into your instance, [These instructions](https://www.linuxsysadmins.com/how-to-connect-to-amazon-ec2-remotely-using-ssh/) should work. Alternatively, check the official AWS Website [here](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/connect-to-linux-instance.html). Basically the command looks like below:

  

  * ```
    ~~~
    ssh -i your-amazon.pem ec2-user@instance-address
    ~~~
    ```


 
### Aims: Upload your binary executable file to EC2 and Run it. 
* After SSH into EC2, make a directory of your choice using mkdir command 
  ```
  #create your directory, if permission denied, add sudo
  (sudo) mkdir <your_dir_name>

  #run following command to allow permission to upload files
  #if permission denied, add sudo
  (sudo) chmod -R 777 <your_dir_name>
  ```

* Open a command prompt windor from your local machine, and use scp to copy the file onto EC2 
```
sudo scp -i <path to your pem file for aws private key, include.pem extension> <path to your executable binary cross-compiled> ec2-user@<EC2_IP_ADDR>:<folder of your choices>
```
- Note the whitespace, if I separate the command by whitespace I should see something like  
```
sudo 
scp 
-i 
<path to your pem file for aws private key, include.pem extension> 
<path to your executable binary cross-compiled> 
ec2-user@<EC2_IP_ADDR>:<folder of your choices>  
```
* Notes for above commands
  - sudo is not required if you open the command prompt from Window with administrator right
  - for the paths, the first two (perm file and compiled file) are paths on your local machine
  - ec2-user is the login name you used when ssh into your ec2 instance
  - EC2_IP_ADDR -> this is the public IP address of your EC2 instance
  - For folder path on EC2, if you previously mkdir folder on directory when you logged in, it will typically be /home/ec2-user/<your_dir_name>

* Now you can try run your go server, navigate to the directory where your upload the file
```
#run your go-server file
./<your-filename>

#do this to change permissions if you get permission denied
sudo chmod -R 777 <your-filename>
```
![image](https://github.com/sjchin88/bsds-6650-TA/assets/71600246/657e0738-9088-4a4c-b737-36e207fa7444)

* Now, using curl / Postman, send an http request to your server. your address should be something like http:/<EC2_IP_ADDR>:8080/<your_url_path>



Once you get this far, life looks pretty good. First mission accomplished! In 3 weeks you'll be able to do all this in your sleep. 

### Notes
Some notes based on first experience with the Learner Lab:

- Download a new key pair when you launch your first instance. You can then use this for all subsequent instances you launch
- a .cer file seems to be the same as a .pem file, so you can use that in ssh commands

Here is some troubleshooting guide if something is not working. 
* If you receive permission error when uploading / running the file
  - try use sudo chmod -R 777 <dir/filename> command to change the permission
* If you receive other error when try to run the executable file on EC2
  - try compile again the file from local into other GOARCH (arm64 or amd64)
* If you can see the server running on SSH interface, but cannot receive valid http responses
  - check your security group setting to allow inbound traffic on required port from your PC
  - check your url path information 

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



[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
