package com.ntnn.workflow;

import com.ntnn.common.AbstractTask;
import com.ntnn.common.TaskWorkFlow;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.TaskData;
import com.ntnn.task.CheckUserRoleTask;
import com.ntnn.task.ProductsDBTask;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class WorkFlowInsertProduct extends TaskWorkFlow {
    public WorkFlowInsertProduct(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CheckUserRoleTask("CheckUserRoleTask", vertx));
        AbstractTask taskInsertProduct = new AbstractTask("TaskInsertProduct", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                JsonObject data = input.getData();
                JsonArray arr = data.getJsonArray("users");
                if(arr.isEmpty() || arr.size() == 0){
                    input.setResult(false);
                    input.setResultCode(BackendErr.NOT_FOUND);
                    input.setData(new JsonObject().put("message", "Can't find user by your input id"));
                    whenDone.handle(input);
                    return;
                }
                JsonObject jo = arr.getJsonObject(0);
                int roleId = jo.getInteger("roleId");
                if(roleId != 1) {
                    input.setResult(false);
                    input.setResultCode(BackendErr.INVALID_ROLE);
                    input.setData(new JsonObject().put("message", "Your role is denied"));
                    whenDone.handle(input);
                    return;
                }
                ProductsDBTask taskInsertProduct = new ProductsDBTask("TaskInsertProduct",
                        vertx, TypeCheck.INSERT);
                taskInsertProduct.run(input, async -> {
                    whenDone.handle(async);
                });
            }
        };
        addTask(taskInsertProduct);
    }
}
