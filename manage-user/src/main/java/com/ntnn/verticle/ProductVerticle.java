package com.ntnn.verticle;

import com.ntnn.constant.Utils;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.task.ProductsDBTask;
import com.ntnn.workflow.WorkFlowAddNumberProducts;
import com.ntnn.workflow.WorkFlowDeleteProduct;
import com.ntnn.workflow.WorkFlowInsertProduct;
import com.ntnn.workflow.WorkFlowUpdateProduct;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductVerticle extends AbstractConsumeVerticle{

    public ProductVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }

    @Override
    public void consume(Message msg) {
        log.info("Call verticle --> ProductVerticle");
        log.info(msg.body().toString());
        JsonObject input = new JsonObject(msg.body().toString());
        TaskData taskData = Utils.getInstance().convertDataTaskJson(input);
        switch (taskData.getQueue()) {
            case "INSERT":
                WorkFlowInsertProduct workFlowInsertProduct =
                        new WorkFlowInsertProduct("WorkFlowInsertProduct", vertx);
                workFlowInsertProduct.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
                break;
            case "SELECT":
                ProductsDBTask productsDBTask = new ProductsDBTask("ProductsDBTask", vertx);
                productsDBTask.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
                break;
            case "SELECT_ID":
                break;
            case "UPDATE":
                WorkFlowUpdateProduct workFlowUpdateProduct =
                        new WorkFlowUpdateProduct("WorkFlowUpdateProduct", vertx);
                workFlowUpdateProduct.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
                break;
            case "DELETE":
                WorkFlowDeleteProduct workFlowDeleteProduct =
                        new WorkFlowDeleteProduct("WorkFlowUpdateProduct", vertx);
                workFlowDeleteProduct.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
                break;
            case "ADD_NUMBER":
                WorkFlowAddNumberProducts workFlowAddNumberProducts =
                        new WorkFlowAddNumberProducts("WorkFlowAddNumberProducts", vertx);
                workFlowAddNumberProducts.run(taskData, whenDone -> {
                    msg.reply(whenDone.toString());
                });
        }
    }
}
