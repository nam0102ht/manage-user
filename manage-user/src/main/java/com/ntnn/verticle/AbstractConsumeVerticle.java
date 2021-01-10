package com.ntnn.verticle;

import com.ntnn.common.ITask;
import com.ntnn.model.TaskData;
import com.ntnn.model.VertDeployCfg;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractConsumeVerticle extends AbstractVerticle implements ITask {
    private TaskData input;
    private final AtomicInteger counter = new AtomicInteger(0);
    private String name;
    protected VertDeployCfg mDeployCfg;
    public AbstractConsumeVerticle(VertDeployCfg mDeployCfg, String name) {
        this.name = name;
        this.mDeployCfg = mDeployCfg;
    }

    @Override
    public void start() {
        vertx.eventBus().consumer(mDeployCfg.address, this::consume);
    }

    public abstract void consume(Message msg);
}
