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
public class WorkFlowOrderTest extends BaseTestTask {
    WorkFlowOrder workFlow;

    @Before
    public void init() {
        super.init();
        workFlow = new WorkFlowOrder("WorkFlowInsertProduct", vertx);
        suite = TestSuite.create(vertx);
    }

    @Test
    public void testOrderIfExistUserOutStock(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ORDERS\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382755\",\"firstName\":\"Van B\",\"lastName\":\"Nguyen\",\"address\":\"128 Los Angel Les, P13, Binh Thanh\",\"email\":\"vanb@gmail.com\"},\"products\":[{\"productId\":3,\"number\":2},{\"productId\":4,\"number\":3}]}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), false);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.OUT_STOCK);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

    @Test
    public void testOrderIfNotExistUserOutStock(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user aren't found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ORDERS\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382766\",\"firstName\":\"Van Cao\",\"lastName\":\"Nguyen\",\"address\":\"12 Newton, P13, Binh Thanh\",\"email\":\"vanao@gmail.com\"},\"products\":[{\"productId\":3,\"number\":2},{\"productId\":4,\"number\":3}]}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), false);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.OUT_STOCK);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }


    @Test
    public void testOrderIfExist(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user aren't found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ORDERS\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382758\",\"firstName\":\"Nhat Nam\",\"lastName\":\"Nguyen Trung\",\"address\":\"73 Ca Van Thinh, p13, Tan Binh\",\"email\":\"nhatnam@gmail.com\"},\"products\":[{\"productId\":3,\"number\":1},{\"productId\":4,\"number\":1}]}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), true);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.SUCCESS);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

    @Test
    public void testOrderIfNotExist(TestContext testContext) {
        Async async = testContext.async();
        suite.test("user aren't found", context -> {
            String str = "{\"resultCode\":200,\"result\":true,\"userId\":0,\"queue\":\"ORDERS\",\"data\":{\"userReq\":{\"phoneNumber\":\"0939382766\",\"firstName\":\"Van Cao\",\"lastName\":\"Nguyen\",\"address\":\"12 Newton, P13, Binh Thanh\",\"email\":\"vanao@gmail.com\"},\"products\":[{\"productId\":3,\"number\":1},{\"productId\":4,\"number\":1}]}}";
            TaskData taskData = new TaskData(str);
            workFlow.run(taskData, whenDone -> {
                log.info(whenDone);
                testContext.assertEquals(whenDone.getResult(), true);
                testContext.assertEquals(whenDone.getResultCode(), BackendErr.SUCCESS);
                async.complete();
            });
        });
        suite.run(vertx);
        async.await(2000L);
    }

}
