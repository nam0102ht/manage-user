package com.ntnn.common;

import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public abstract class AbstractTask extends Task {
    private TaskData input;

    public AbstractTask(String name, Vertx vertx) {
        super(name, vertx);
    }

    @Override
    public Vertx getVertx() {
        return super.getVertx();
    }

    public void run(TaskData input, final Handler<TaskData> whenDone) {
        this.input = input;
        exec(input, hanlder -> {
            whenDone.handle(hanlder);
        });
    }

    protected abstract void exec(TaskData input, Handler<TaskData> whenDone);
}
