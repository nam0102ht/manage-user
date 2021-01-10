package com.ntnn.task;

import com.ntnn.common.UracTask;
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
        }
    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String prioritySQL = "SELECT * \n" +
                "FROM manageUser.person p\n";
        client.call(prioritySQL, resultSetAsyncResult -> {
            if (resultSetAsyncResult.succeeded()) {
                ResultSet res = resultSetAsyncResult.result();
                List<JsonObject> rows = res.getRows();
                JsonArray list = new JsonArray();
                rows.forEach(row -> {
                    JsonObject person = convertPerson(row);
                    list.add(person);
                });
                input.setResult(true);
                input.setResultCode(0);
                input.setData(new JsonObject().put("users", list));
                whenDone.handle(input);
                return;
            }
            input.setResult(false);
            input.setResultCode(400);
            input.setData(new JsonObject());
            whenDone.handle(input);
        });
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        JsonObject extras = input.getData();
        Persons person = new Persons(extras.toString());
        JsonArray paramsPersons = createParamsInsert(person);
        String priorityParams = "INSERT INTO manageUser.person(firstname, lastname, dob, address, email, phoneNumber, extras, roleId, teamId) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        client.querySingleWithParams(priorityParams,
                paramsPersons,
                resultSetAsyncResult -> {
                    if(resultSetAsyncResult.succeeded()) {
                        input.setResultCode(0);
                        input.setResult(true);
                        whenDone.handle(input);
                        return;
                    }
                    input.setResultCode(500);
                    input.setResult(false);
                    log.error(resultSetAsyncResult.cause().getMessage());
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
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    public JsonObject convertPerson(JsonObject row) {
        JsonObject person = new JsonObject()
                .put("id", row.getLong("id"))
                .put("firstName", row.getString("firstName"))
                .put("lastName", row.getString("lastName"))
                .put("dob", row.getLong("dob"))
                .put("address", row.getString("address"))
                .put("roleId", row.getInteger("roleId"))
                .put("email", row.getString("email"))
                .put("extras", row.getString("extras"))
                .put("teamId",row.getInteger("teamId"));
        return person;
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
                .add(person.getRoleId())
                .add(person.getTeamId());
    }
}
