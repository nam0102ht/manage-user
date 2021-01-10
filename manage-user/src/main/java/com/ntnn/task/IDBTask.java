package com.ntnn.task;

import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.ext.jdbc.JDBCClient;

public interface IDBTask {
    void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client);
    void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client);
    void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client);
    void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client);
    void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client);
}
