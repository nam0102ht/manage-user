package com.ntnn.verticle;

import com.ntnn.constant.BackendErr;
import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.workflow.WorkFlowInsertProduct;
import com.ntnn.workflow.WorkFlowOrder;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderVerticle extends AbstractConsumeVerticle{
    public OrderVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }
    @Override
    public void consume(Message msg) {
        log.info("Call verticle --> OrderVerticle");
        log.info(msg.body().toString());
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
        switch (taskData.getQueue()) {
            case "ORDERS":
                WorkFlowOrder workFlowOrder = new WorkFlowOrder("WorkFlowOrder", vertx);
                workFlowOrder.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
                break;
            default:
                taskData.setResultCode(BackendErr.FAIL);
                taskData.setResult(false);
                msg.reply(taskData);
                break;
        }
    }
}
