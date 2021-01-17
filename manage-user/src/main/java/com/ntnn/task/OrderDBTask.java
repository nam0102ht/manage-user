package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.Orders;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderDBTask extends UracTask implements IDBTask {

    private final TypeCheck typeCheck;

    public OrderDBTask(String name, Vertx vertx, TypeCheck typeCheck) {
        super(name, vertx);
        this.typeCheck = typeCheck;
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        switch (this.typeCheck) {
            case SELECT:
                doSelect(input, whenDone, client);
                break;
            case INSERT:
                doInsert(input, whenDone, client);
                break;
            case DELETE:
                doDelete(input, whenDone, client);
                break;
            case UPDATE:
                doUpdate(input, whenDone, client);
                break;
        }
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        JsonObject joUser = extras.getJsonArray("users").getJsonObject(0);
        JsonArray joProducts = extras.getJsonArray("products");
        JsonObject convertData = new JsonObject()
                .put("name", "Order by: "+joUser.getLong("personId"))
                .put("personId", joUser.getLong("id"))
                .put("extras", "")
                .put("products", joProducts.toString());
        Orders orders = new Orders(convertData.toString());
        JsonArray paramsPersons = createParamsInsert(orders);
        String priorityParams = "INSERT INTO  `manage-user`.orders(name, personId, extras, products)\n" +
                "VALUES (?, ?, ?, ?);";
        try {
            client.querySingleWithParams(priorityParams,
                    paramsPersons,
                    resultSetAsyncResult -> {
                        if (resultSetAsyncResult.succeeded()) {
                            input.setResultCode(0);
                            input.setResult(true);
                            whenDone.handle(input);
                            return;
                        }
                        input.setResultCode(500);
                        input.setResult(false);
                        log.error(resultSetAsyncResult.cause().getMessage());
                        whenDone.handle(input);
                    });
        } catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }
    public JsonArray createParamsInsert(Orders orders) {
        return new JsonArray()
                .add(orders.getName())
                .add(orders.getPersonId())
                .add(orders.getExtras())
                .add(orders.getProducts());
    }
}
