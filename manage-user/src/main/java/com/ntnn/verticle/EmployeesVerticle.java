package com.ntnn.verticle;

import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class EmployeesVerticle extends AbstractConsumeVerticle {
    public EmployeesVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }

    @Override
    public void consume(Message msg) {
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
    }
}
