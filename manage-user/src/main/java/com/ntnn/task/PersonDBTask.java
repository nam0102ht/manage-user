package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.BackendErr;
import com.ntnn.constant.TypeCheck;
import com.ntnn.model.Persons;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import lombok.extern.log4j.Log4j2;

import java.util.List;


@Log4j2
public class PersonDBTask extends UracTask implements IDBTask{

    private final TypeCheck typeCheck;
    private static final String USER_REQUEST = "userReq";

    public PersonDBTask(String name, Vertx vertx) {
        super(name, vertx);
        typeCheck = TypeCheck.SELECT;
    }

    public PersonDBTask(String name, TypeCheck typeCheck,Vertx vertx) {
        super(name, vertx);
        this.typeCheck = typeCheck;
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        switch (this.typeCheck) {
            case SELECT:
                doSelect(input, whenDone, client);
                break;
            case INSERT:
                doInsert(input, whenDone, client);
                break;
            case DELETE:
                doDelete(input, whenDone, client);
                break;
            case UPDATE:
                doUpdate(input, whenDone, client);
                break;
            case SELECT_ID:
                doSelectById(input, whenDone, client);
                break;
        }
    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String prioritySQL = "SELECT p.* FROM `manage-user`.person p;";
        try {
            client.call(prioritySQL, resultSetAsyncResult -> {
                if (resultSetAsyncResult.succeeded()) {
                    ResultSet res = resultSetAsyncResult.result();
                    List<JsonObject> rows = res.getRows();
                    JsonArray list = new JsonArray();
                    rows.forEach(row -> {
                        list.add(row);
                    });
                    input.setResult(true);
                    input.setResultCode(0);
                    JsonObject obj = input.getData();
                    input.setData(obj.put("users", list));
                    whenDone.handle(input);
                    return;
                }
                input.setResult(false);
                input.setResultCode(400);
                input.setData(new JsonObject());
                whenDone.handle(input);
                return;
            });
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(USER_REQUEST, new JsonObject());
        Persons person = new Persons(jo.toString());
        JsonArray paramsPersons = createParamsInsert(person);
        String priorityParams = "INSERT INTO `manage-user`.person(firstname, lastname, dob, address, email, phoneNumber, extras, roleId) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            client.querySingleWithParams(priorityParams,
                    paramsPersons,
                    resultSetAsyncResult -> {
                        if (resultSetAsyncResult.succeeded()) {
                            input.setResultCode(BackendErr.SUCCESS);
                            input.setResult(true);
                            whenDone.handle(input);
                            return;
                        }
                        input.setResultCode(BackendErr.FAIL);
                        input.setResult(false);
                        log.error(resultSetAsyncResult.cause().getMessage());
                        whenDone.handle(input);
                    });
        } catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(USER_REQUEST, new JsonObject());
        Persons person = new Persons(jo.toString());
        JsonArray paramsPersons = createParamsUpdate(person);
        String priorityParams = "DELETE FROM `manage-user`.person p WHERE p.id = ?;";
        try {
            client.querySingleWithParams(priorityParams,
                    paramsPersons,
                    resultSetAsyncResult -> {
                        if (resultSetAsyncResult.succeeded()) {
                            input.setResultCode(BackendErr.SUCCESS);
                            input.setResult(true);
                            whenDone.handle(input);
                            return;
                        }
                        input.setResultCode(BackendErr.FAIL);
                        input.setResult(false);
                        log.error(resultSetAsyncResult.cause().getMessage());
                        whenDone.handle(input);
                    });
        } catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(USER_REQUEST, new JsonObject());
        Persons person = new Persons(jo.toString());
        JsonArray paramsPersons = createParamsDelete(person);
        String priorityParams = "UPDATE `manage-user`.person p SET p.firstname = ?, p.lastname = ?, p.dob = ?, p.address = ?, " +
                "p.email = ? , p.phoneNumber = ?, p.extras = ?, p.roleId = ?" +
                " WHERE p.id = ?";
        try {
            client.querySingleWithParams(priorityParams,
                    paramsPersons,
                    resultSetAsyncResult -> {
                        if (resultSetAsyncResult.succeeded()) {
                            input.setResultCode(BackendErr.SUCCESS);
                            input.setResult(true);
                            whenDone.handle(input);
                            return;
                        }
                        input.setResultCode(BackendErr.FAIL);
                        input.setResult(false);
                        log.error(resultSetAsyncResult.cause().getMessage());
                        whenDone.handle(input);
                    });
        } catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String prioritySQL = "SELECT p.* FROM `manage-user`.person p WHERE p.phoneNumber = ?;";
        JsonObject extras = input.getData();
        JsonObject jo = extras.getJsonObject(USER_REQUEST, new JsonObject());
        Persons person = new Persons(jo.toString());
        JsonArray paramsPersons = new JsonArray().add(person.getPhoneNumber());
        try {
            client.queryWithParams(prioritySQL, paramsPersons, resultSetAsyncResult -> {
                if (resultSetAsyncResult.succeeded()) {
                    ResultSet res = resultSetAsyncResult.result();
                    List<JsonObject> rows = res.getRows();
                    JsonArray list = new JsonArray();
                    rows.forEach(row -> {
                        list.add(row);
                    });
                    input.setResult(true);
                    input.setResultCode(BackendErr.SUCCESS);
                    JsonObject obj = input.getData();
                    input.setData(obj.put("users", list));
                    whenDone.handle(input);
                    return;
                }
                input.setResult(false);
                input.setResultCode(BackendErr.FAIL);
                input.setData(new JsonObject());
                whenDone.handle(input);
                return;
            });
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    public JsonArray createParamsInsert(Persons person) {
        return new JsonArray()
                .add(person.getFirstName())
                .add(person.getLastName())
                .add(person.getDob())
                .add(person.getAddress())
                .add(person.getEmail())
                .add(person.getPhoneNumber())
                .add(person.getExtras())
                .add(person.getRoleId());
    }

    public JsonArray createParamsUpdate(Persons person) {
        return new JsonArray()
                .add(person.getFirstName())
                .add(person.getLastName())
                .add(person.getDob())
                .add(person.getAddress())
                .add(person.getEmail())
                .add(person.getPhoneNumber())
                .add(person.getExtras())
                .add(person.getRoleId())
                .add(person.getId());
    }

    public JsonArray createParamsDelete(Persons person) {
        return new JsonArray()
                .add(person.getId());
    }
}
