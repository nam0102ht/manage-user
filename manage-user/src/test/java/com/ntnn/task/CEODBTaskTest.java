package com.ntnn.task;

import com.ntnn.model.TaskData;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class CEODBTaskTest extends BaseTestTask {
    CEODBTask task;
    @Before
    public void init() {
        super.init();
        task = new CEODBTask("CEODBTask", vertx);
    }
    @Test
    public void doTest(TestContext testContext) {
        Async async = testContext.async();
        suite.test("select data success", context -> {
            TaskData input = new TaskData();
            task.run(input, whenDone -> {
                testContext.assertEquals(whenDone.getResult(), true);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }
}
