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

public class TeamsDepartmentDBTask extends UracTask implements IDBTask {
    public TeamsDepartmentDBTask(String name, Vertx vertx) {
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
        client = getSqlClient();
        String sql = "select\n" +
                "    JSON_OBJECT(\n" +
                "        'deparmentId', d.id,\n" +
                "        'name', d.name,\n" +
                "        'teams', JSON_OBJECT(\n" +
                "            'teamId', t.id,\n" +
                "            'name', t.name\n" +
                "        )\n" +
                "    ) as teams\n" +
                "from manageUser.department d join manageUser.teams t on d.id = t.departmentId;";
        client.query(sql, resultSetAsyncResult -> {
            if(resultSetAsyncResult.succeeded()) {
                ResultSet resultSet = resultSetAsyncResult.result();
                List<JsonArray> lst = resultSet.getResults();
                JsonArray row;
                JsonArray res = new JsonArray();
                JsonObject department = input.getData();
                int i;
                for(i=0; i<lst.size();i++){
                    row = lst.get(i);
                    res.add(new JsonObject(row.getString(0)));
                }
                input.setResultCode(0);
                input.setResult(true);
                input.setData(department.put("teams", res));
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
