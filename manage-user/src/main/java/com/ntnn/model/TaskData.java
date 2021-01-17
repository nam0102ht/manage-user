package com.ntnn.model;

import com.ntnn.constant.TypeCheck;
import io.vertx.core.cli.annotations.ConvertedBy;
import io.vertx.core.json.JsonObject;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TaskData {
    private String requestId;
    private Integer resultCode;
    private Boolean result;
    private String queue;
    private long userId;
    private JsonObject data;

    public TaskData() {}

    public TaskData(String str) {
        if(str == null){ return; }
        JsonObject jo = new JsonObject(str);
        this.requestId = jo.getString("requestId", "");
        this.resultCode = jo.getInteger("resultCode", 0);
        this.result = jo.getBoolean("result", false);
        this.queue = jo.getString("queue", "");
        this.data = jo.getJsonObject("data", new JsonObject());
    }

    public String toString(){
        return new JsonObject()
                .put("requestId", this.requestId)
                .put("resultCode", this.resultCode)
                .put("result", this.result)
                .put("queue", this.queue)
                .put("userId", this.userId)
                .put("data", data).toString();
    }
}
