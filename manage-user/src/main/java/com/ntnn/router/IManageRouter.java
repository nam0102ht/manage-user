package com.ntnn.router;

import io.vertx.ext.web.RoutingContext;

public interface IManageRouter {
    void addUser(RoutingContext context);
    void addDepartment(RoutingContext context);
    void employees(RoutingContext context);
}
