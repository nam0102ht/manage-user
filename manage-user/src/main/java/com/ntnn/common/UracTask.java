package com.ntnn.common;

import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import lombok.extern.log4j.Log4j2;

import java.io.File;

@Log4j2
public abstract class UracTask extends AbstractTask {
    private static JDBCClient mSQLClient;
    public UracTask(String name, Vertx vertx) {
        super(name, vertx);
    }

    public JDBCClient getSqlClient() {
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
}
