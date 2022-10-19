# Lab 7: 

# 1. Playing with AWS Lambda

### Aims:

Deploy a simple AWS Lambda function

### Instructions

Follow the tutorial [here](https://www.baeldung.com/java-aws-lambda) to deploy an AWS Lambda Function

Additional AWS docs can he found [here](https://docs.aws.amazon.com/lambda/latest/dg/lambda-java.html).

# 2. Connecting Tomcat Servlet to MySQL using JDBC

## Aims

In this lab, your will be able to add JDBC driver to your project, so that your Java project can access the MySQL database, locally or remotely.

## Step 1: Add JDBC driver for MySQL and DB Connection Pooling library to your project

Add the following to the `<dependencies>` tag of your `pom.xml` and wait for your IDE to resolve dependencies.

```
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.18</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2 -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.7.0</version>
</dependency>
```

## Step 2: Set up DB connection manager
With JDBC driver downloaded, we are able to let our Java program talk to MySQL database. So next we will create such connection manager class to establish connections between your project and MySQL database.

```java
import org.apache.commons.dbcp2.*;

public class DBCPDataSource {
    private static BasicDataSource dataSource;

    // NEVER store sensitive information below in plain text!
    private static final String HOST_NAME = System.getProperty("MySQL_IP_ADDRESS");
    private static final String PORT = System.getProperty("MySQL_PORT");
    private static final String DATABASE = "LiftRides";
    private static final String USERNAME = System.getProperty("DB_USERNAME");
    private static final String PASSWORD = System.getProperty("DB_PASSWORD");

    static {
        // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
        dataSource = new BasicDataSource();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);
        dataSource.setUrl(url);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(60);
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}
```
Because the code above contains very sensitive information such as public database server IP and port, and the DB access credentials, you should NEVER hard code those info in your code which will be PUBLIC on GitHub. Instead, [store them as system properties](https://stackoverflow.com/a/16566920/3949193) in `TOMCAT_HOME/conf/catalina.properties`, and retrieve them using 

    System.getProperty("MySQL_IP_ADDRESS")

in your Java code.

There are a lot of parameters available to [configure the DBCP](https://tomcat.apache.org/tomcat-9.0-doc/jndi-datasource-examples-howto.html#Database_Connection_Pool_(DBCP_2)_Configurations). Do read the documents and try to achieve the best performance by playing around with these params.


## Step 3: Create a [DAO layer](https://en.wikipedia.org/wiki/Data_access_object) between your Servlets and DB

Below is an example DAO class with a record insertion operation. Your code may vary depending on your DB schema design.

```java
import java.sql.*;
import org.apache.commons.dbcp2.*;

public class LiftRideDao {
    private static BasicDataSource dataSource;

    public LiftRideDao() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public void createLiftRide(LiftRide newLiftRide) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String insertQueryStatement = "INSERT INTO LiftRides (skierId, resortId, seasonId, dayId, time, liftId) " +
                                      "VALUES (?,?,?,?,?,?)";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(insertQueryStatement);
            preparedStatement.setInt(1, newLiftRide.getSkierId());
            preparedStatement.setInt(2, newLiftRide.getResortId());
            preparedStatement.setInt(3, newLiftRide.getSeasonId());
            preparedStatement.setInt(4, newLiftRide.getDayId());
            preparedStatement.setInt(5, newLiftRide.getTime());
            preparedStatement.setInt(6, newLiftRide.getLiftId());

            // execute insert SQL statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
```

Usually it is the DAO classes where data is directly accessed from the database, and where POJO is passed into.

After this step is done, you may create a simple test program and check if records can be successfully inserted to your database.

```java
public static void main(String[] args) {
    LiftRideDao liftRideDao = new LiftRideDao();
    liftRideDao.createLiftRide(new LiftRide(10, 2, 3, 5, 500, 20));
}
```

## Step 4: Access DB through DAO objects in Servlets
This step should be very straightforward once you have tested that your DAO object works. For example, in `POST` method on `/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}`, in your `doPost` method in the corresponding Servlet, you basically need to 
 - extract values from URL path params and request body
 - construct a `LiftRide` object with those values, and 
 - pass that object to the DAO layer

of course, don't forget path validations and some other necessary steps.

[Back to Course Home Page](https://gortonator.github.io/bsds-6650/)