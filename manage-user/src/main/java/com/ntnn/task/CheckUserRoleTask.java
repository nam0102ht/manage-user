package com.ntnn.task;

import com.ntnn.common.AbstractTask;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.TaskData;
import com.ntnn.verticle.AuthVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class CheckUserRoleTask extends AbstractTask {

    public CheckUserRoleTask(String name, Vertx vertx) {
        super(name, vertx);
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        PersonDBTask dbTaskSelect = new PersonDBTask(AuthVerticle.class.getName(),
                TypeCheck.SELECT_ID, getVertx());
        dbTaskSelect.run(input, async -> {
            if (!async.getResult()) {
                async.setData(new JsonObject().put("message", "Can't select user"));
                async.setResult(false);
                async.setResultCode(BackendErr.FAIL);
                whenDone.handle(async);
                return;
            }
            whenDone.handle(async);
        });
    }
}
