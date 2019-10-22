# Lab 6: Connecting Tomcat Servlet to MySQL using JDBC

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
import java.sql.*;
import org.apache.commons.dbcp2.*;

public class DBCPDataSource {
    private static BasicDataSource dataSource = new BasicDataSource();

    // NEVER store sensitive information below in plain text!
    private static final String HOST_NAME = System.getenv("MySQL_IP_ADDRESS");
    private static final String PORT = System.getenv("MySQL_PORT");
    private static final String DATABASE = "LiftRides";
    private static final String USERNAME = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
        String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);
        dataSource.setUrl(url);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
```
Because the code above contains very sensitive information such as public database server IP and port, and the DB access credentials, you should NEVER hard code those info in your code which will be PUBLIC on GitHub. Instead, [store them as environment variables in your EC2 instances](https://stackoverflow.com/questions/50668315/set-environment-variables-in-an-aws-instance), and retrieve them using 

    System.getenv("ENV_VAR_KEY")

in your Java code.

There are a lot of parameters available to [configure the DBCP](https://tomcat.apache.org/tomcat-9.0-doc/jndi-datasource-examples-howto.html#Database_Connection_Pool_(DBCP_2)_Configurations). Do read the documents and try to achieve the best performance by playing around with these params.


## Step 3: Create a [DAO layer](https://en.wikipedia.org/wiki/Data_access_object) between your Servlets and DB

Below is an example DAO class with a record insertion operation. Your code may vary depending on your DB schema design.

```java
import java.sql.*;

public class LiftRideDao {
    private static Connection conn;
    private static PreparedStatement preparedStatement;

    public LiftRideDao() {
        try {
            conn = DBCPDataSource.getConnection();
            preparedStatement = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createLiftRide(LiftRide newLiftRide) {
        String insertQueryStatement = "INSERT INTO LiftRides (skierId, resortId, seasonId, dayId, time, liftId) " +
                                      "VALUES (?,?,?,?,?,?)";
        try {
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
