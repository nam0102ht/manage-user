package com.ntnn.constant;

import com.ntnn.model.TaskData;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Utils {

    public static Utils INSTANCE = new Utils();
    public static Utils getInstance() {
        return INSTANCE;
    }

    public TaskData convertDataTaskJson(JsonObject jo){
        TaskData taskData = new TaskData();
        taskData.setUserId(jo.getLong("userId"));
        taskData.setQueue(jo.getString("queue"));
        taskData.setResult(jo.getBoolean("result"));
        taskData.setResultCode(jo.getInteger("resultCode"));
        taskData.setData(jo.getJsonObject("data"));
        return taskData;
    }
}
