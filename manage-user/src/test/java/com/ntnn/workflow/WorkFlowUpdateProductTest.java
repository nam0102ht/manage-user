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
public class WorkFlowUpdateProductTest extends BaseTestTask {
    WorkFlowUpdateProduct workFlow;

    @Before
    public void init() {
        super.init();
        workFlow = new WorkFlowUpdateProduct("WorkFlowUpdateProduct", vertx);
        suite = TestSuite.create(vertx);
    }

    @Test
    public void testUserNotFound(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user aren't found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"UPDATE\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382111\"},\"productReq\":{\"id\":3,\"name\":\"Mantantu Top\",\"colorId\":1,\"price\":280,\"brandId\":2,\"description\":\"This is Mantantu top update update\"}}}";
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
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"UPDATE\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382799\"},\"productReq\":{\"id\":3,\"name\":\"Mantantu Top\",\"colorId\":1,\"price\":280,\"brandId\":2,\"description\":\"This is Mantantu top update update\"}}}";
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
        suite.test("user delete success", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"UPDATE\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382758\"},\"productReq\":{\"id\":3,\"name\":\"Mantantu Top\",\"colorId\":1,\"price\":280,\"brandId\":2,\"description\":\"This is Mantantu top update update\"}}}";
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
