package com.ntnn.task;

import com.ntnn.common.ITask;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(VertxUnitRunner.class)
public abstract class BaseTestTask {
    protected Vertx vertx;
    protected TestSuite suite;
    protected ITask iTask;

    @Before
    public void init(){
        vertx = Vertx.vertx();
        iTask = Mockito.mock(ITask.class);
        suite = TestSuite.create(this.getClass().getSimpleName());
        Mockito.when(iTask.getVertx()).thenReturn(vertx);
    }
}
