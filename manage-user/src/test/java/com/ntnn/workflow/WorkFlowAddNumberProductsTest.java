package com.ntnn.workflow;

import com.ntnn.model.TaskData;
import com.ntnn.task.BaseTestTask;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class WorkFlowAddNumberProductsTest extends BaseTestTask {
    WorkFlowAddNumberProducts workFlow;

    @Before
    public void init() {
        super.init();
        workFlow = new WorkFlowAddNumberProducts("WorkFlowAddNumberProductsTest", vertx);
        suite = TestSuite.create(vertx);
    }

    @Test
    public void faildCauseSystem(TestContext testContext) {
        Async async = testContext.async();
        suite.test("faild cause system", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"INSERT\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382758\"},\"product\":{\"name\":\"Banana Bottom\",\"colorId\":3,\"price\":560,\"brandId\":1,\"description\":\"This is Banana Bottom\"}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                testContext.assertEquals(whenDone.getResult(), false);
                testContext.assertEquals(whenDone.getResultCode(), 500);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

    @Test
    public void testUserNotRole(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user is denied", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"INSERT\",\"data\":{\"userReq\":{\"phoneNumber\":\"0123456789\"},\"product\":{\"name\":\"Banana Bottom\",\"colorId\":3,\"price\":560,\"brandId\":1,\"description\":\"This is Banana Bottom\"}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                testContext.assertEquals(whenDone.getResult(), false);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

    @Test
    public void testUserInsertSuccess(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user insert success", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"INSERT\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382758\"},\"productReq\":{\"name\":\"Cococa Nata\",\"colorId\":3,\"price\":800,\"brandId\":1,\"description\":\"This is Cococa Conata\"}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                testContext.assertEquals(whenDone.getResult(), false);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }
}
