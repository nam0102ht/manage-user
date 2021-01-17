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

public class WorkFlowUpdateProduct extends TaskWorkFlow {

    public WorkFlowUpdateProduct(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CheckUserRoleTask("CheckUserRoleTask", vertx));
        AbstractTask taskUpdateProduct = new AbstractTask("taskUpdateProduct", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                JsonObject data = input.getData();
                JsonArray arr = data.getJsonArray("users");
                if(arr.isEmpty() || arr.size() == 0) {
                    input.setResultCode(BackendErr.NOT_FOUND);
                    input.setData(new JsonObject().put("message", "User is exist already"));
                    whenDone.handle(input);
                    return;
                }
                ProductsDBTask productsDBTask =
                        new ProductsDBTask("productsDBTask", vertx, TypeCheck.UPDATE);
                productsDBTask.run(input, async -> {
                    whenDone.handle(async);
                });
            }
        };
        addTask(taskUpdateProduct);
    }

}
