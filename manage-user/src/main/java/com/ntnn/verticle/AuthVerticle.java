package com.ntnn.verticle;

import com.ntnn.constant.TypeCheck;
import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.task.PersonDBTask;
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
        log.info(msg.body().toString());
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
        switch (taskData.getQueue()){
            case "INSERT":
                PersonDBTask dbTask = new PersonDBTask(AuthVerticle.class.getName(),
                        TypeCheck.INSERT, vertx);
                dbTask.run(taskData, async -> {
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
        }
    }

}
