# CS6650 Fall 2020

## Lab 2 - Java Concurrency and Servlets

1. Experiment with Java threads
2. Build a Java Servlet to process two APIs that are similar to your assignment tasks.
3. Echo the API parameter values back to the client
4. Learn simple testing with POSTMAN

# Java Multithreaded Exercises

## Counter

Write a Java multithreaded programs that

1. takes a time stamp, and start N threads, default 1K
2. each thread calls a method that increments a shared synchronized counter 10 times, and then terminates
3. when all threads are completed, the main thread takes a time stamp and prints out the counter value and the duration it takes to run the program

Run the program with a variable number of threads (e.g.1,10k etc) and see if you can observe any relationship between number of threads and total run time?

## Collections (1)

Write a Java program that uses a single thread to add a lot of elements (100k?) to a:

- Vector
- ArrayList

Time how long each test takes to quantify the overheads of synchronization.

## Collections (2)

Write a Java program that uses a single thread to add a lot of elements (100k?) to a:

- HashTable
- HashMap
- ConcurrentHashMap
1. Time how long each test takes to quantify the overheads of synchronization.
2. Make your program mulththreaded with e.g 100 threads and and again compare performance.

You will have to make access to the HashMap threadsafe using Collections.synchronizedMap.

## Investigate the costs of file access and context switching

You aim is to write a multithreaded program that writes information to a test file as fast as possible.

Start by creating a Java program that opens a text file for writing.

Then create 500 threads and in each thread generate 1000 strings of the format:
"timestamp, Thread-id, N"

where:

1. _timestamp_ is the output of calling _System.currentTimeMillis();_
2. _Thread-id_ is result of calling _Thread.currentThread().getId();_
3. _N_ is the loop iteration, i.e. between 0 and 999

Your aim to to store all these generated strings from all threads into the same file as quickly as possible. 
The order they are written to the file is initially unimportant. 

Time how long the program takes by taking a timestamp immediately before creating any threads and another when all threads are completed and the file is written and closed.

Approaches you can experiment with are along the lines of:

1. write every string to the file immediately after it is generated in the loop in each thread
2. write _all_ the strings from one thread after they are generated and just before a thread terminates
3. Store all the strings from all threads in a shared collection, and write this to a file from your main() thread after all threads are completed
4. others?

Implement two or more of these approaches and compare the wall time for the program.

What insights do you take away from this exercise?

Extensions:

1. Test your solutions with threads that generate 2000 strings rather than 1000. What about 5000? 10000?
2. Can you design a solution in which only one thread writes to the file _while_ the threads are generating the strings.
3. How would you modify any of your solutions to ensure the data is written to the file in ascending timestamp order?
   
   

## Java Servlets

**First, make sure you are using the latest version of IntelliJ. Old versions seem to have build problems**

### Step 1: Create a new Maven project in IntelliJ

1. Open the **File** menu, point to **New** and click **Project**
2. Choose **Maven** in the left pane, select the project SDK and click **Next**. Chose teh SDK version that matches the version installed on your EC2 instance.
3. Specify `GroupId`, `ArtifactId`, `Version` and click **Next**
   - [Guide to naming conventions on groupId, artifactId, and version](https://maven.apache.org/guides/mini/guide-naming-conventions.html)

### Step 2: Add Web Application support to the project

1. In IntelliJ, right click on the project root directory and click **Add Framework Support**.
2. Choose **Web Application** and Click **OK**

This will create a simple web application structure in your project

### Step 3: Create Servlets

1. The API we'll work from is on [Swagger](https://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/1.1#/free). We'll just work on the POST and GET for /skiers, not the full API.

2. Add Servlet dependency to your [pom.xml](https://maven.apache.org/pom.html#What_is_the_POM) (use your own `groupId` and `artifactId`)
   
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>cs6650-lab</groupId>
       <artifactId>cs6650-lab</artifactId>
       <version>1.0-SNAPSHOT</version>
   
   <dependencies>
       <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
       <dependency>
           <groupId>javax.servlet</groupId>
           <artifactId>javax.servlet-api</artifactId>
           <version>4.0.1</version>
           <scope>provided</scope>
       </dependency>
   
   </dependencies>
   </project>
   ```
   
   - IntelliJ should resolve dependencies right after this new dependency is added

3. Right click `src/main/java` directory, point to **New** and click **Create New Servlet**.
   
   - Enter the name of the servlet, e.g. "SkierServlet"
     - IntelliJ will then create the skeleton of the servlet with `doGet` and `doPost` methods
   - Make sure the `java` directory is marked as sources root, which should be done by default

4. Create servlet mapping in [web.xml](https://docs.oracle.com/cd/E13222_01/wls/docs92/webapp/configureservlet.html) by adding code below inside the `web-app` tag
   
   ```xml
       <servlet>
           <servlet-name>SkierServlet</servlet-name>
           <servlet-class>SkierServlet</servlet-class>
       </servlet>
   
       <servlet-mapping>
           <servlet-name>SkierServlet</servlet-name>
           <url-pattern>/skiers/*</url-pattern>
       </servlet-mapping>
   ```

5. Add `doGet` and `doPost` methods to handle `GET`/`POST` requests matching `/skiers/*` which is the url patterns you specified in `<servlet-mapping>` tag in `web.xml` shown above. You can use the code below as your starting point.
   
   ```java
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       res.setContentType("text/plain");
       String urlPath = req.getPathInfo();
   
       // check we have a URL!
       if (urlPath == null || urlPath.isEmpty()) {
           res.setStatus(HttpServletResponse.SC_NOT_FOUND);
           res.getWriter().write("missing paramterers");
           return;
       }
   
       String[] urlParts = urlPath.split("/");
       // and now validate url path and return the response status code
       // (and maybe also some value if input is valid)
   
       if (!isUrlValid(urlParts)) {
           res.setStatus(HttpServletResponse.SC_NOT_FOUND);
       } else {
           res.setStatus(HttpServletResponse.SC_OK);
           // do any sophisticated processing with urlParts which contains all the url params
           // TODO: process url params in `urlParts`
           res.getWriter().write("It works!");
       }
   }
   
   private boolean isUrlValid(String[] urlPath) {
       // TODO: validate the request url path according to the API spec
       // urlPath  = "/1/seasons/2019/day/1/skier/123"
       // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
       return true;
   }
   ```
   
    You can use similar URL validation approach for `doPost`. 
    Additionally, according to the API spec, the `POST` method to the same URL accepts a JSON request body. We can parse that information with `req.getReader()` which returns a `BufferedReader`, which can be [easily converted to a String](https://stackoverflow.com/questions/5516020/bufferedreader-read-multiple-lines-into-a-single-string) for further JSON processing.

### Step 4: Install Tomcat **locally** and set up Tomcat in IntelliJ

This step is optional but highly recommended. By doing so, we can make sure our servlets fully work in our local Tomcat server before we are confident enough to deploy it to the server in EC2. You can also set breakpoints in doGet/doPost methods, and the debugger will be triggered once the corresponding request comes.

**Important:**

- **install the same version (we can support v8 and 9) of Tomcat on both your EC2 instances and locally.**  Avoid version 10 - there are new features that may cause problems. 
- **build your servlet with the same Java version as you have installed on your EC2 instance. This will eliminate 'weird' errors that you might get downstream.**
1. [Install Tomcat on your machine](#install-tomcat)
2. [Add Tomcat server run configuration on IntelliJ](https://www.mkyong.com/intellij/intellij-idea-run-debug-web-application-on-tomcat/)

When that's done, if you run Tomcat server inside IntelliJ, you should see console output similar to this. 

```
......
......
......

16-Sep-2019 03:42:06.991 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["http-nio-8080"]
16-Sep-2019 03:42:07.085 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["ajp-nio-8009"]
16-Sep-2019 03:42:07.099 INFO [main] org.apache.catalina.startup.Catalina.load Server initialization in [682] milliseconds
16-Sep-2019 03:42:07.242 INFO [main] org.apache.catalina.core.StandardService.startInternal Starting service [Catalina]
16-Sep-2019 03:42:07.243 INFO [main] org.apache.catalina.core.StandardEngine.startInternal Starting Servlet engine: [Apache Tomcat/9.0.24]
16-Sep-2019 03:42:07.271 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
16-Sep-2019 03:42:07.326 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
16-Sep-2019 03:42:07.366 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [266] milliseconds
Connected to server
[2019-09-16 03:42:07,397] Artifact cs6650lab:war exploded: Artifact is being deployed, please wait...
[2019-09-16 03:42:08,111] Artifact cs6650lab:war exploded: Artifact is deployed successfully
[2019-09-16 03:42:08,111] Artifact cs6650lab:war exploded: Deploy took 714 milliseconds
```

And if you visit http://localhost:8080/[WEB_APP]/skiers/12/seasons/2019/day/1/skier/123 (no brackets, same below), (hopefully) you should see the response (`"It works!"`) processed by `doGet` method in your servlet.

If you get a 404 page, make sure `WEB_APP` matches **Application Context** specified in **Run/Debug Configurations** -> **Tomcat Server** -> **Deployment** tab.

### Step 5: Deploy WAR file to Tomcat server in EC2

In IntelliJ:

1. Open the **Build** menu and click **Build Artifacts** and click "Edit" in "Action"

2. In "Artifacts" tab of Project Settings, click "+" to add "Web Application: Archive" for "[WEB_APP]:war exploded" and click "OK" to finish.

3. Open the **Build** menu and click **Build Artifacts** and click "Build" for "[WEB_APP]:war"
   
   - A WAR file called "[WEB_APP].war" will be created in `out/artifacts/[WEB_APP]/[WEB_APP].war`

4. Upload this file to the `webapps` folder in your Tomcat installation path in the EC2 instance with tools such as [scp](http://man7.org/linux/man-pages/man1/scp.1.html), for example
   
   - $ `sudo scp -i /path/to/pem/file /local/path/to/war/file ec2-user@EC2_IP_ADDR:/remote/path/to/tomcat_webapp/directory`
     The exact location is dependent on installation choices and version but there's a good chance it will be something like '/var/lib/tomcat8/webapps' or alternatively /usr/share/tomcat8/webapps
     If you get permission denied, you'll have to use chmod as follows:
     
     ```
     cd /var/lib/tomcat8
     sudo chmod -R 777 webapps/
     ```

5. Visit **http://{YOUR_REMOTE_INSTANCE_IP}:8080/[WEB_APP]/skiers/12/seasons/2019/day/1/skier/123** and you should see the same response as what you get locally

### Step 6: Send GET/POST request to server with Postman

`GET` request is very straightforward as it can be easily done by visiting the URL in your favorite web browser.

Since `POST` requests usually also contain request body, we can utilize tools like [Postman](https://www.getpostman.com/downloads/) to include those additional information.

1. Choose `POST` as request method and enter the request URL

2. In "Body" tab, choose `raw` and enter the JSON request body in the text area, for example
   
   ```json
       {
           "time": 217,
           "liftID": 21
       }
   ```
   
    then click "Send".
    If all goes well, you will be able to see the response produced by your `doPost` method.

Congratulations! You have just finished one of the most tricky part of all assignments! It may take a lot of pain to set up everything correctly and get your dev environment ready, but I assure you much more exciting stuff is coming up next!

### Reference Stuff:

- [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

- <a href='#install-tomcat' id='install-tomcat' class='anchor'>Installing Tomcat on</a>
  
  - [macOS](http://www.herongyang.com/JSP/macOS-Download-and-Install-Tomcat.html)
  - [Windows](https://tomcat.apache.org/tomcat-9.0-doc/setup.html#Windows)
  - [Ubuntu](https://www.digitalocean.com/community/tutorials/install-tomcat-9-ubuntu-1804)

- Finding a file - example Linux command 
  
  - find / -name "tomcat-users.xml" 2>/dev/null
    
    #### Common HTTP Response Codes

- 200: Done, it was okay. Generally, your GETs return this code.

- 201: “Done, and created.” Generally, your POSTs return this code.

- 204: “Done, and no body.” Generally, your DELETEs return this code.

- 400: “Client sent me junk, and I’m not going to mess with it.”

- 401: “Unauthorized, the client should authenticate first.”

- 403: “Not allowed. You can’t have it because you logged in but don’t have permission to this thing or to delete this thing.”

- 404: “Can’t find it.”

- 410: “Marked as deleted.”

- 451: “The government made me not show it.”

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)
