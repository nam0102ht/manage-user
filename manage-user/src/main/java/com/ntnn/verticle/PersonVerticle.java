package com.ntnn.verticle;

import com.ntnn.model.VertDeployCfg;
import io.vertx.core.eventbus.Message;

public class PersonVerticle extends AbstractConsumeVerticle{
    public PersonVerticle(VertDeployCfg mDeployCfg, String name) {
        super(mDeployCfg, name);
    }

    @Override
    public void consume(Message msg) {

    }
}
