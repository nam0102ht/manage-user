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
    private String userId;
    private JsonObject data;
    public TaskData() {}

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
