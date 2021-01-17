package com.ntnn.workflow;

import com.ntnn.common.AbstractTask;
import com.ntnn.common.TaskWorkFlow;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.TaskData;
import com.ntnn.task.CheckUserRoleTask;
import com.ntnn.task.SubProductDBTask;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class WorkFlowAddNumberProducts extends TaskWorkFlow {
    public WorkFlowAddNumberProducts(String name, Vertx vertx) {
        super(name, vertx);
        addTask(new CheckUserRoleTask("CheckUserRoleTask", vertx));
        addTask(new AbstractTask("AddNumberTask", vertx) {
            @Override
            protected void exec(TaskData input, Handler<TaskData> whenDone) {
                SubProductDBTask insertSubProduct =
                        new SubProductDBTask("InsertSubProduct", vertx, TypeCheck.INSERT);
                insertSubProduct.run(input, async -> { whenDone.handle(input); });
            }
        });
    }
}
