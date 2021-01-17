package com.ntnn.router;

import com.ntnn.model.TaskData;
import com.ntnn.verticle.AuthVerticle;
import com.ntnn.verticle.OrderVerticle;
import com.ntnn.verticle.ProductVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
public class MainRouter implements IManageRouter {

    public Vertx vertx;
    public MainRouter(Vertx vertx){
        this.vertx = vertx;
    }

    @Override
    public void addUser(RoutingContext context) {
        HttpServerResponse response = context.response();
        context.request().bodyHandler(bodyHandler -> {
            final JsonObject body = bodyHandler.toJsonObject();
            log.info("call person verticle by: " +body.toString());
            TaskData taskData = new TaskData();
            taskData.setRequestId(UUID.randomUUID().toString());
            taskData.setResult(body.getBoolean("result"));
            taskData.setResultCode(body.getInteger("resultCode"));
            taskData.setQueue(body.getString("queue"));
            taskData.setData(body.getJsonObject("data"));
            taskData.setUserId(body.getLong("userId"));
            vertx.eventBus().send(AuthVerticle.class.getName(), taskData.toString(), reply -> {
                // Write to the response and end it
                response.putHeader("content-type", "application/json");
                response.end(reply.result().body().toString());
            });
        });
    }

    @Override
    public void updateProducts(RoutingContext context) {
        HttpServerResponse response = context.response();
        context.request().bodyHandler(bodyHandler -> {
            final JsonObject body = bodyHandler.toJsonObject();
            log.info("Call update verticle by: " + body.toString());
            TaskData taskData = new TaskData();
            taskData.setRequestId(UUID.randomUUID().toString());
            taskData.setResult(body.getBoolean("result"));
            taskData.setResultCode(body.getInteger("resultCode"));
            taskData.setQueue(body.getString("queue"));
            taskData.setData(body.getJsonObject("data"));
            taskData.setUserId(body.getLong("userId"));
            vertx.eventBus().send(ProductVerticle.class.getName(), taskData.toString(), reply -> {
                // Write to the response and end it
                response.putHeader("content-type", "application/json");
                response.end(reply.result().body().toString());
            });
        });
    }

    @Override
    public void orderProduct(RoutingContext context) {
        HttpServerResponse response = context.response();
        context.request().bodyHandler(bodyHandler -> {
            JsonObject body = bodyHandler.toJsonObject();
            log.info("Call orders by: " + body.toString());
            TaskData taskData = new TaskData();
            taskData.setRequestId(UUID.randomUUID().toString());
            taskData.setResult(body.getBoolean("result"));
            taskData.setResultCode(body.getInteger("resultCode"));
            taskData.setQueue(body.getString("queue"));
            taskData.setData(body.getJsonObject("data"));
            taskData.setUserId(body.getLong("userId"));
            vertx.eventBus().send(OrderVerticle.class.getName(), taskData.toString(), reply -> {
                // Write to the response and end it
                response.putHeader("content-type", "application/json");
                response.end(reply.result().body().toString());
            });
        });
    }


}
