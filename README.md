# manage-user
RUN file SQL in folder sql
RUN manage-user.sql to create table
THEN run all file to create data

All project use:
LIB: Vertx
Languages: Java 1.8
Database use: MYSQL

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

RUN project via IDE