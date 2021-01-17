package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.SubProducts;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class SubProductDBTask extends UracTask implements IDBTask {
    private final TypeCheck typeCheck;
    private final String SUB_PRODUCT = "sub_products";

    public SubProductDBTask(String name, Vertx vertx, TypeCheck typeCheck) {
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
        JsonObject jo = extras.getJsonObject(SUB_PRODUCT, new JsonObject());
        SubProducts products = new SubProducts(jo.toString());
        JsonArray params = new JsonArray().add(products.getProductId());
        String sql = "INSERT INTO `manage-user`.sub_products(productId) VALUES (?)";
        int numbers = jo.getInteger("number", 1);
        int i;
        for(i = 0; i < numbers-1;i++) {
            sql += ", (?) ";
            params.add(products.getProductId());
        }
        try{
            client.queryWithParams(sql, params, resultSetAsyncResult -> {
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
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        JsonObject subData = extras.getJsonObject(SUB_PRODUCT, new JsonObject());
        SubProducts products = new SubProducts(subData.toString());
        JsonArray params = new JsonArray()
                .add(products.getId());
        String sql = "UPDATE `manage-user`.sub_products sb SET sb.isCell = 1 WHERE sb.id = ?;";
        try{
            client.queryWithParams(sql, params, resultSetAsyncResult -> {
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
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String prioritySQL = "SELECT\n" +
                "    sp.id as subId,\n" +
                "    p.id as productId,\n" +
                "    p.name as name,\n" +
                "    p.price as price,\n" +
                "    p.colorId as colorId,\n" +
                "    c.name as colorName,\n" +
                "    p.brandId as brandId,\n" +
                "    b.name as brandName,\n" +
                "    sp.isCell as isCell\n" +
                "FROM `manage-user`.products p\n" +
                "LEFT JOIN `manage-user`.sub_products sp on p.id = sp.productId\n" +
                "LEFT JOIN `manage-user`.color c on p.colorId = c.id\n" +
                "LEFT JOIN `manage-user`.brand b on p.brandId = b.id\n" +
                "WHERE isCell = 0\n" +
                "AND productId = ?\n" +
                "ORDER BY sp.id ASC;";
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject("product", new JsonObject());
        SubProducts products = new SubProducts(jo.toString());
        JsonArray params = new JsonArray().add(products.getProductId());
        log.info("Query: "+prioritySQL);
        try {
            client.queryWithParams(prioritySQL, params,resultSetAsyncResult -> {
                if (resultSetAsyncResult.succeeded()) {
                    ResultSet res = resultSetAsyncResult.result();
                    List<JsonObject> rows = res.getRows();
                    JsonArray list = new JsonArray();
                    rows.forEach(row -> {
                        list.add(row);
                    });
                    input.setResult(true);
                    input.setResultCode(0);
                    JsonObject obj = input.getData();
                    obj.put("products", list);
                    input.setData(obj);
                    whenDone.handle(input);
                    return;
                }
                input.setResult(false);
                input.setResultCode(400);
                input.setData(new JsonObject());
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String prioritySQL = "SELECT\n" +
                "    sp.id as subId,\n" +
                "    p.id as productId,\n" +
                "    p.name as name,\n" +
                "    p.price as price,\n" +
                "    p.colorId as colorId,\n" +
                "    c.name as colorName,\n" +
                "    p.brandId as brandId,\n" +
                "    b.name as brandName,\n" +
                "    sp.isCell as isCell\n" +
                "FROM `manage-user`.products p\n" +
                "LEFT JOIN `manage-user`.sub_products sp on p.id = sp.productId\n" +
                "LEFT JOIN `manage-user`.color c on p.colorId = c.id\n" +
                "LEFT JOIN `manage-user`.brand b on p.brandId = b.id\n" +
                "WHERE isCell = 0\n" +
                "AND subId = ?\n" +
                "ORDER BY sp.id ASC;";
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(SUB_PRODUCT, new JsonObject());
        SubProducts products = new SubProducts(jo.toString());
        JsonArray params = new JsonArray().add(products.getId());
        try {
            client.queryWithParams(prioritySQL, params,resultSetAsyncResult -> {
                if (resultSetAsyncResult.succeeded()) {
                    ResultSet res = resultSetAsyncResult.result();
                    List<JsonObject> rows = res.getRows();
                    JsonArray list = new JsonArray();
                    rows.forEach(row -> {
                        list.add(row);
                    });
                    input.setResult(true);
                    input.setResultCode(0);
                    input.setData(new JsonObject().put("users", list));
                    whenDone.handle(input);
                    return;
                }
                input.setResult(false);
                input.setResultCode(400);
                input.setData(new JsonObject());
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

}
