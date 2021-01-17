package com.ntnn.task;

import com.ntnn.common.AbstractTask;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.SubProducts;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class AutoChooseSubProduct extends AbstractTask {
    public AutoChooseSubProduct(String name, Vertx vertx) {
        super(name, vertx);
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        SubProductDBTask subProductDBTask =
                new SubProductDBTask("SubProductDBTask", getVertx(), TypeCheck.SELECT);
        subProductDBTask.run(input, async -> {
            if(async.getResult() == true) {
                JsonObject obj = async.getData();
                JsonArray arrProduct = obj.getJsonArray("products");
                if(arrProduct.size() == 0 ) {
                    input.setResultCode(BackendErr.OUT_STOCK);
                    input.setResult(false);
                    whenDone.handle(input);
                    return;
                }
                List<JsonObject> lst = arrProduct.getList();
                JsonObject anySubProduct = lst.stream().findAny().get();
                int subId = anySubProduct.getInteger("subId");
                int productId = anySubProduct.getInteger("productId");
                obj.put("sub_products", new SubProducts(subId, productId, true).toJsonObject());
                input.setData(obj);
                SubProductDBTask subProductDBTaskUpdate =
                        new SubProductDBTask("SubProductDBTaskInsert", getVertx(), TypeCheck.UPDATE);
                subProductDBTaskUpdate.run(input, async2 -> {
                    whenDone.handle(async2);
                });
            }
        });
    }
}
