package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.Products;
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
public class ProductsDBTask extends UracTask implements IDBTask {
    private final TypeCheck typeCheck;
    private final String PRODUCT_REQ = "productReq";

    public ProductsDBTask(String name, Vertx vertx, TypeCheck typeCheck) {
        super(name, vertx);
        this.typeCheck = typeCheck;
    }

    public ProductsDBTask(String name, Vertx vertx) {
        super(name, vertx);
        this.typeCheck = TypeCheck.SELECT;
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        switch (this.typeCheck) {
            case SELECT:
                doSelect(input, whenDone, client);
                break;
            case DELETE:
                doDelete(input, whenDone, client);
                break;
            case INSERT:
                doInsert(input, whenDone, client);
                break;
            case UPDATE:
                doUpdate(input, whenDone, client);
                break;
            case SELECT_ID:
                doSelectById(input, whenDone, client);
                break;
        }
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "INSERT INTO `manage-user`.products(name, colorId, price, brandId, description)" +
                " VALUES(?,?,?,?,?);";
        client = getSqlClient();
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(PRODUCT_REQ, new JsonObject());
        Products products = new Products(jo.toString());
        JsonArray params = createParams(products);
        try{
            client.querySingleWithParams(sql,params,value -> {
                if(value.failed()){
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(value.cause().getMessage());
                    whenDone.handle(input);
                    return;
                }
                log.info("Result insert: "+value.result());
                input.setResultCode(0);
                input.setResult(true);
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "DELETE FROM `manage-user`.products WHERE id = ?";
        client = getSqlClient();
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(PRODUCT_REQ, new JsonObject());
        Products products = new Products(jo.toString());
        JsonArray params = createParamsDelete(products);
        try{
            client.querySingleWithParams(sql,params,value -> {
                if(value.failed()){
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(value.cause().getMessage());
                    whenDone.handle(input);
                    return;
                }
                input.setResultCode(0);
                input.setResult(true);
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "UPDATE `manage-user`.products" +
        " SET name = ?, colorId = ?, price = ?, brandId = ?, description = ?  WHERE id = ?;";
        client = getSqlClient();
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(PRODUCT_REQ, new JsonObject());
        Products products = new Products(jo.toString());
        JsonArray params = createParamsUpdate(products);
        try{
            client.querySingleWithParams(sql,params,value -> {
                if(value.failed()){
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(value.cause().getMessage());
                    whenDone.handle(input);
                    return;
                }
                input.setResultCode(0);
                input.setResult(true);
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "SELECT\n" +
                "    p.*,\n" +
                "    c.name as colorName,\n" +
                "    b.name as brandName\n" +
                "FROM `manage-user`.products p\n" +
                "LEFT JOIN `manage-user`.color c on p.colorId = c.id\n" +
                "LEFT JOIN `manage-user`.brand b on p.brandId = b.id\n";
        JsonObject obj = input.getData();
        int sort = obj.getInteger("sort", 1);
        String filter = obj.getString("filter", "name");
        String regex = obj.getString("regex", ".");
        sql += "WHERE p."+filter+" REGEXP '"+regex+"' \n";
        if(sort == 1) {
            sql += "ORDER BY p."+filter+" DESC\n";
        } else {
            sql += "ORDER BY p."+filter+" ASC\n";
        }
        int limitFrom = obj.getInteger("limitFrom", 0);
        int limitTo = obj.getInteger("limitTo", 1000);
        if(limitFrom != 0 || limitTo != 1000) {
            sql += "LIMIT "+limitFrom+", "+limitTo+";";
        }
        log.info("Syntax SQL: "+sql);
        client = getSqlClient();
        try{
            client.query(sql,value -> {
                if(value.failed()){
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(value.cause().getMessage());
                    whenDone.handle(input);
                    return;
                }
                ResultSet res = value.result();
                List<JsonObject> rows = res.getRows();
                JsonArray list = new JsonArray();
                rows.forEach(row -> {
                    JsonObject product = convertProduct(row);
                    list.add(product);
                });
                input.setResultCode(0);
                input.setResult(true);
                input.setData(new JsonObject().put("products", list));
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "SELECT\n" +
                "    p.id,\n" +
                "    p.name,\n" +
                "    p.colorId,\n" +
                "    p.price,\n" +
                "    p.brandId,\n" +
                "    p.description,\n" +
                "    c.name as colorName,\n" +
                "    b.name as brandName\n" +
                "FROM `manage-user`.products p\n" +
                "LEFT JOIN `manage-user`.color c on p.colorId = c.id\n" +
                "LEFT JOIN `manage-user`.brand b on p.brandId = b.id\n" +
                "WHERE p.id = ?;";
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(PRODUCT_REQ, new JsonObject());
        Products products = new Products(jo.toString());
        client = getSqlClient();
        JsonArray params = new JsonArray().add(products.getId());
        try{
            client.queryWithParams(sql,params,value -> {
                if(value.failed()){
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(value.cause().getMessage());
                    whenDone.handle(input);
                    return;
                }
                ResultSet res = value.result();
                List<JsonObject> rows = res.getRows();
                JsonArray list = new JsonArray();
                rows.forEach(row -> {
                    JsonObject product = convertProduct(row);
                    list.add(product);
                });
                input.setResultCode(0);
                input.setResult(true);
                input.setData(new JsonObject().put("products", list));
                whenDone.handle(input);
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
    public JsonArray createParams(Products products){
        return new JsonArray()
                .add(products.getName())
                .add(products.getColorId())
                .add(products.getPrice())
                .add(products.getBrandId())
                .add(products.getDescription());
    }

    public JsonArray createParamsUpdate(Products products){
        return new JsonArray()
                .add(products.getName())
                .add(products.getColorId())
                .add(products.getPrice())
                .add(products.getBrandId())
                .add(products.getDescription())
                .add(products.getId());
    }
    public JsonArray createParamsDelete(Products products){
        return new JsonArray()
                .add(products.getId());
    }
    public JsonObject convertProduct(JsonObject row) {
        JsonObject product = new JsonObject()
                .put("id", row.getInteger("id"))
                .put("name", row.getString("name"))
                .put("colorId", row.getInteger("colorId"))
                .put("colorName", row.getString("colorName"))
                .put("price", row.getDouble("price"))
                .put("brandId", row.getInteger("brandId"))
                .put("brandName", row.getString("brandName"))
                .put("description", row.getString("description"));
        return product;
    }
}
