package com.ntnn.workflow;

import com.ntnn.constant.BackendErr;
import com.ntnn.model.TaskData;
import com.ntnn.task.BaseTestTask;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@Log4j2
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
    public void testUserNotFound(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user aren't found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ADD_NUMBER\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382733\"},\"sub_products\":{\"productId\":4,\"number\":5}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), false);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.NOT_FOUND);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

    @Test
    public void testUserAccessDenied(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user access deny", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ADD_NUMBER\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382799\"},\"sub_products\":{\"productId\":4,\"number\":5}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), false);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.INVALID_ROLE);
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
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ADD_NUMBER\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382758\"},\"sub_products\":{\"productId\":4,\"number\":5}}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                testContext.assertEquals(whenDone.getResult(), true);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.SUCCESS);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }
}
