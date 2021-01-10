package com.ntnn.task;

import com.ntnn.common.UracTask;
import com.ntnn.constant.TypeCheck;
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
public class DepartmentManagerDBTask extends UracTask implements IDBTask {
    private TypeCheck typeCheck;
    public DepartmentManagerDBTask(String name, Vertx vertx){
        super(name, vertx);
    }

    public DepartmentManagerDBTask(String name, Vertx vertx, TypeCheck typeCheck){
        super(name, vertx);
        this.typeCheck = typeCheck;
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
        String sql = "select JSON_OBJECT('id', d.id, 'name', d.name, 'manager', JSON_OBJECT('id', p.id, 'fistname', p.firstname, 'lastname', p.lastname, \n" +
                "            'dob', p.dob, 'address', p.address, 'email', p.email, 'phoneNumber', p.phoneNumber, \n" +
                "            'extras', p.extras)) as departments \n" +
                "from manageUser.department d \n" +
                "join manageUser.person p on p.id = d.personId \n";
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
                input.setData(deparment.put("department", res));
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

    @Override
    protected void exec(TaskData input, Handler<TaskData> whenDone) {
        JDBCClient client = getSqlClient();
        doSelect(input, whenDone, client);
    }
}
