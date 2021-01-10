package com.ntnn.common;

import io.vertx.core.Vertx;

public abstract class Task implements ITask{
    private String name;
    private Vertx vertx;
    public Task(String name, Vertx vertx) {
        this.name = name;
        this.vertx = vertx;
    }

    public Vertx getVertx() {
        return this.vertx;
    }
}
