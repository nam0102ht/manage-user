package com.ntnn.verticle;

import com.ntnn.constant.TypeCheck;
import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.task.DepartmentDBTask;
import com.ntnn.task.PersonDBTask;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class DepartmentVerticle extends AbstractConsumeVerticle {

    public DepartmentVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }

    @Override
    public void consume(Message msg) {
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
        switch (taskData.getQueue()){
            case "INSERT":
                DepartmentDBTask task = new DepartmentDBTask("DepartmentDBTask", vertx, TypeCheck.INSERT);
                break;
            case "SELECT":
                DepartmentDBTask selectTask = new DepartmentDBTask("DepartmentDBTask", vertx, TypeCheck.SELECT);
                selectTask.run(taskData, async -> {
                    msg.reply(async);
                });
                break;
            default:
                break;
        }
    }
}
