package com.ntnn.verticle;

import com.ntnn.router.IManageRouter;
import com.ntnn.router.MainRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VertxMain extends AbstractVerticle {
    public static final String API_USER = "/api/v2/users";
    public static final String API_PRODUCTS = "/api/v2/products";
    public static final String API_ORDER = "/api/v2/orders";

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        // Main router
        IManageRouter mainRouter = new MainRouter(vertx);
        router.route(HttpMethod.POST, API_USER)
                .handler(mainRouter::addUser);
        router.route(HttpMethod.POST, API_PRODUCTS)
                .handler(mainRouter::updateProducts);
        router.route(HttpMethod.POST, API_ORDER)
                .handler(mainRouter::orderProduct);

        // Create Server
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router).listen(8081, httpServerAsyncResult -> {
            if(httpServerAsyncResult.succeeded()) {
                log.info("---Made by: Nguyen Trung Nhat Nam--");
                log.info("-- Name project: Manage Orders ----");
                log.info("Service is running with port: "+8081);
                log.info("-----------------------------------");
                log.info("---Router is running at VertxMain--");
                log.info("-----------------------------------");
            }
        });
    }
}
