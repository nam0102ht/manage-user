package com.ntnn.router;

import io.vertx.ext.web.RoutingContext;

public interface IManageRouter {
    void addUser(RoutingContext context);
    void updateProducts(RoutingContext context);
    void orderProduct(RoutingContext context);
}
