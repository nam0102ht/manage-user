package com.ntnn.verticle;

import com.ntnn.constant.TypeCheck;
import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.task.PersonDBTask;
import com.ntnn.workflow.WorkFlowInsertUser;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthVerticle extends AbstractConsumeVerticle {

    public AuthVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }

    @Override
    public void consume(Message msg) {
        log.info("Call verticle --> AuthVerticle");
        log.info(msg.body().toString());
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
        switch (taskData.getQueue()){
            case "INSERT":
                WorkFlowInsertUser workFlowInsertUser = new WorkFlowInsertUser("WorkFlowInsert", vertx);
                workFlowInsertUser.run(taskData, async -> {
                    msg.reply(taskData.toString());
                });
                break;
            case "SELECT":
                PersonDBTask dbTaskSelect = new PersonDBTask(AuthVerticle.class.getName(),
                        TypeCheck.SELECT, vertx);
                dbTaskSelect.run(taskData, async -> {
                    msg.reply(taskData.toString());
                });
                break;
            case "UPDATE":
                PersonDBTask dbTaskUpdate = new PersonDBTask(AuthVerticle.class.getName(),
                        TypeCheck.UPDATE, vertx);
                dbTaskUpdate.run(taskData, async -> {
                    msg.reply(taskData.toString());
                });
                break;
            case "DELETE":
                PersonDBTask dbTaskDelete = new PersonDBTask(AuthVerticle.class.getName(),
                        TypeCheck.DELETE, vertx);
                dbTaskDelete.run(taskData, async -> {
                    msg.reply(taskData.toString());
                });
                break;
        }
    }

}
