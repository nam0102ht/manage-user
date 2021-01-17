package com.ntnn.workflow;

import com.ntnn.common.AbstractTask;
import com.ntnn.common.TaskWorkFlow;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.SubProducts;
import com.ntnn.model.TaskData;
import com.ntnn.task.*;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class WorkFlowOrder extends TaskWorkFlow {
    public WorkFlowOrder(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CheckUserRoleTask("CheckUserExist", vertx));
        addTask(new AbstractTask("InsertUserWhenNotFound", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                JsonObject data = input.getData();
                JsonArray arr = data.getJsonArray("users");
                // if not exist, it wil be added
                if(arr.isEmpty() || arr.size() == 0){
                    PersonDBTask personDBTask =
                            new PersonDBTask("insertDBTask", TypeCheck.INSERT, vertx);
                    personDBTask.run(input, async -> {
                        whenDone.handle(async);
                    });
                    return;
                }
                // if exist it will return
                whenDone.handle(input);
            }
        });
        addTask(new AbstractTask("UpdateListProductsTask", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                JsonObject data = input.getData();
                JsonArray ja = data.getJsonArray("products");
                int i;
                for(i =0; i< ja.size(); i++) {
                    AutoChooseSubProduct autoChooseSubProduct =
                            new AutoChooseSubProduct("AutoChooseValue", vertx);
                    data.put("product", ja.getJsonObject(i));
                    autoChooseSubProduct.run(input, async -> {
                        whenDone.handle(input);
                    });
                }
            }
        });
        addTask(new AbstractTask("SaveOrderData", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                OrderDBTask orderDBTask =
                        new OrderDBTask("InsertDataToDB", vertx, TypeCheck.INSERT);
                orderDBTask.run(input, async -> {
                    whenDone.handle(async);
                });
            }
        });
    }
}
