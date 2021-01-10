package com.ntnn;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ntnn.model.VertDeployCfg;
import com.ntnn.task.DepartmentDBTask;
import com.ntnn.verticle.AuthVerticle;
import com.ntnn.verticle.DepartmentVerticle;
import com.ntnn.verticle.EmployeesVerticle;
import com.ntnn.verticle.VertxMain;
import io.vertx.core.Vertx;

public class ServiceMain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        VertDeployCfg mDeployCfg = new VertDeployCfg();
        mDeployCfg.address = AuthVerticle.class.getName();
        VertDeployCfg mDeployCfgDepartment = new VertDeployCfg();
        mDeployCfgDepartment.address = DepartmentVerticle.class.getName();
        VertDeployCfg mDeployCfgEmployees = new VertDeployCfg();
        mDeployCfgEmployees.address = EmployeesVerticle.class.getName();
        vertx.deployVerticle(new AuthVerticle(mDeployCfg, "AuthVerticle"));
        vertx.deployVerticle(new DepartmentVerticle(mDeployCfgDepartment, "DepartmentVerticle"));
        vertx.deployVerticle(new EmployeesVerticle(mDeployCfgEmployees, "EmployeesVerticle"));
        vertx.deployVerticle(VertxMain.class.getName());
    }
}
