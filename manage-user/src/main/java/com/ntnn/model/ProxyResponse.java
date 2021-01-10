package com.ntnn.model;

import io.vertx.core.json.JsonObject;

public class ProxyResponse {
    private String resultCode;
    private String userId;
    private JsonObject data;

    public ProxyResponse() {}

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
