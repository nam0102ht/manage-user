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

## What should know?
```
1. I'm using vertx you should flow 3 Main Class to read code (ServiceMain, VertxMain, ${name}Verticle)
2. Focus class MainRouter to see event bus send to consum
3. Focus data request Exp: "queue": "INSERT", in here it will push to WorkFlow
4. Can see name to descript WorkFlow do works
```
### Guide use insert products
####We will insert product through curl: 
```
curl --location --request POST 'localhost:8081/api/v2/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "resultCode": 200,
    "result": true,
    "userId": 0,
    "queue": "INSERT",
    "data": {
        "userReq": {
            "phoneNumber": "0939382758"
        },
        "productReq": {
            "name": "Cococa Nata",
            "colorId": 3,
            "price": 800,
            "brandId": 1,
            "description": "This is Cococa Conata"
        }
    }
}'
```
####Then we will insert number product through curl: 
```
curl --location --request POST 'localhost:8081/api/v2/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "resultCode": 200,
    "result": true,
    "userId": 0,
    "queue": "ADD_NUMBER",
    "data": {
        "userReq": {
            "phoneNumber": "0939382758"
        },
        "sub_products": {
            "productId": 4,
            "number": 5
        }
    }
}'
```


