# manage-user

#### Table of contents
* [Run file script SQL](#run-file-script-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Diagram Database](#diagram-database)

## Run file script SQL
RUN file SQL in folder sql
RUN manage-user.sql to create table and Data

## Technologies
All project use:
```
LIB: Vertx
Languages: Java 1.8
Database use: MYSQL
```
## Setup
Config SQL at UracTask.java

```public JDBCClient getSqlClient() {
        if (mSQLClient == null) {
            JsonObject mSQLClientConfig = new JsonObject()
                    .put("url", "jdbc:mysql://localhost:3306/manageUser")
                    .put("driver_class", "com.mysql.jdbc.Driver")
                    .put("user", "root")
                    .put("password", "root")
                    .put("max_pool_size", 30);
            mSQLClient = JDBCClient.createShared(getVertx(), mSQLClientConfig);
            log.info("Get MySQL client!");
        }
        return mSQLClient;
    }
```   
## RUN
RUN project via IDE

## Diagram Database
![Algorithm schema](./img/modelDatabase.PNG)

## Test API
I'm use Postman to test API, you can import from postman-api/vdc-test.postman_collection.json

## Flow API

