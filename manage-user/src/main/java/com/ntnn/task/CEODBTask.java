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

public class CEODBTask extends UracTask implements IDBTask{

    public CEODBTask(String name, Vertx vertx) {
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
                "    JSON_OBJECT('personId', p.id, 'firstname', p.firstname,\n" +
                "     'lastname', p.lastname, 'dob', p.dob,\n" +
                "     'address', p.address, 'email', p.email, 'phoneNumber', p.phoneNumber)\n" +
                "from manageUser.person p\n" +
                "join manageUser.role r on r.id = p.roleId\n" +
                "where roleId = 1;";
        client.query(sql, resultSetAsyncResult -> {
            if(resultSetAsyncResult.succeeded()) {
                ResultSet resultSet = resultSetAsyncResult.result();
                List<JsonArray> lst = resultSet.getResults();
                JsonArray row;
                JsonArray res = new JsonArray();
                int i;
                for(i=0; i<lst.size();i++){
                    row = lst.get(i);
                    res.add(new JsonObject(row.getString(0)));
                }
                input.setResultCode(0);
                input.setResult(true);
                input.setData(new JsonObject().put("ceo", res));
                whenDone.handle(input);
                return;
            }
            input.setResult(false);
            input.setResultCode(500);
            whenDone.handle(input);
        });
    }

    @Override
    public void doSelectById(TaskData input, Handler<TaskData> whenDone, JDBCClient client) {
    }
}
