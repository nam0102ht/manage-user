package com.ntnn;

import com.ntnn.model.VertDeployCfg;
import com.ntnn.verticle.*;
import io.vertx.core.Vertx;

public class ServiceMain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        VertDeployCfg mDeployCfg = new VertDeployCfg();
        mDeployCfg.address = AuthVerticle.class.getName();
        VertDeployCfg mDeployCfgEmployees = new VertDeployCfg();
        mDeployCfgEmployees.address = EmployeesVerticle.class.getName();
        VertDeployCfg mDeployCfgProduct = new VertDeployCfg();
        mDeployCfgProduct.address = ProductVerticle.class.getName();
        VertDeployCfg mDeployCfgOrder = new VertDeployCfg();
        mDeployCfgOrder.address = OrderVerticle.class.getName();

        vertx.deployVerticle(new AuthVerticle(mDeployCfg, "AuthVerticle"));
        vertx.deployVerticle(new EmployeesVerticle(mDeployCfgEmployees, "EmployeesVerticle"));
        vertx.deployVerticle(new ProductVerticle(mDeployCfgProduct, "ProductVerticle"));
        vertx.deployVerticle(new OrderVerticle(mDeployCfgOrder, "OrderVerticle"));
        vertx.deployVerticle(VertxMain.class.getName());
    }
}
