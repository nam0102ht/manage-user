package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.Department;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;

public class DepartmentDBTask extends UracTask implements IDBTask {
    private TypeCheck typeCheck;

    public DepartmentDBTask(String name, Vertx vertx) {
        super(name, vertx);
    }

    public DepartmentDBTask(String name, Vertx vertx, TypeCheck typeCheck) {
        super(name, vertx);
        this.typeCheck = typeCheck;
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        switch (typeCheck){
            case INSERT:
                doInsert(input, whenDone, client);
                break;
            case SELECT:
                doSelect(input, whenDone, client);
                break;
            default:
                break;
        }
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        Department department = new Department(input.getData().toString());
        JsonArray params = new JsonArray()
                .add(department.getName())
                .add(department.getDecription())
                .add(department.getPersonId());
        client = getSqlClient();
        String sqlInsert = "INSERT INTO manageUser.department(name, description, personId) " +
                "VALUES(?,?,?);";
        client.querySingleWithParams(sqlInsert, params, jsonArrayAsyncResult -> {
            if(jsonArrayAsyncResult.succeeded()) {
                input.setResult(true);
                input.setResultCode(0);
                whenDone.handle(input);
                return;
            }
            input.setResult(false);
            input.setResultCode(500);
            whenDone.handle(input);
        });
    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        Department department = new Department(input.getData().toString());
        JsonArray params = new JsonArray()
                .add(department.getName())
                .add(department.getDecription())
                .add(department.getPersonId());
        client = getSqlClient();
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }
}
