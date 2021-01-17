package com.ntnn.workflow;

import com.ntnn.common.AbstractTask;
import com.ntnn.common.TaskWorkFlow;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.TaskData;
import com.ntnn.task.CheckUserRoleTask;
import com.ntnn.task.PersonDBTask;
import com.ntnn.verticle.AuthVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class WorkFlowInsertUser extends TaskWorkFlow {
    public WorkFlowInsertUser(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CheckUserRoleTask("CheckUserRoleTask", vertx));
        AbstractTask taskInsertPerson = new AbstractTask("TaskInsertUser", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                JsonObject data = input.getData();
                JsonArray arr = data.getJsonArray("users");
                if(arr.size() != 0) {
                    input.setResultCode(BackendErr.NOT_FOUND);
                    input.setData(new JsonObject().put("message", "User is exist already"));
                    whenDone.handle(input);
                    return;
                }
                PersonDBTask dbTaskInsert = new PersonDBTask(AuthVerticle.class.getName(),
                        TypeCheck.INSERT, vertx);
                dbTaskInsert.run(input, async -> {
                    if(!async.getResult()) {
                        async.setData(new JsonObject().put("message", "Can't select user"));
                        return;
                    }
                    whenDone.handle(async);
                });
            }
        };
        addTask(taskInsertPerson);
    }
}
