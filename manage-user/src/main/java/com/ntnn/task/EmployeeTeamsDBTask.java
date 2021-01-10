package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.model.TaskData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;

import java.util.List;

public class EmployeeTeamsDBTask extends UracTask implements IDBTask{

    public EmployeeTeamsDBTask(String name, Vertx vertx) {
        super(name, vertx);
    }

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        doSelect(input, whenDone, client);
    }

    @Override
    public void doInsert(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doDelete(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doUpdate(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }

    @Override
    public void doSelect(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
        String sql = "select\n" +
                "    JSON_OBJECT('teamId', t.id, 'teamName', t.name, 'departmentId', t.departmentId, 'personId', p.id, 'firstname', p.firstname,\n" +
                "     'lastname', p.lastname, 'dob', p.dob, 'address', p.address, 'email', p.email, 'phoneNumber', p.phoneNumber) as obj\n" +
                "from manageUser.teams t\n" +
                "join manageUser.teams_persons tp on tp.teamId = t.id\n" +
                "join manageUser.person p on tp.personId = p.id\n" +
                "limit 0, 1500;";
        client.query(sql, resultSetAsyncResult -> {
            if(resultSetAsyncResult.succeeded()){
                ResultSet result = resultSetAsyncResult.result();
                List<JsonArray> lstResult = result.getResults();
                JsonArray row;
                JsonArray res = new JsonArray();
                JsonObject deparment = input.getData();
                int i;
                for(i=0; i<lstResult.size();i++){
                    row = lstResult.get(i);
                    res.add(new JsonObject(row.getString(0)));
                }
                input.setResultCode(0);
                input.setResult(true);
                input.setData(deparment.put("employees", res));
                whenDone.handle(input);
                return;
            }
            input.setResultCode(500);
            input.setResult(false);
            whenDone.handle(input);
        });
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {

    }
}
