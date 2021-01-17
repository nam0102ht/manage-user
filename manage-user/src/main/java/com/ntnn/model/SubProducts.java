package com.ntnn.model;

import io.vertx.core.json.JsonObject;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubProducts {
    private int id;
    private int productId;
    private boolean isCell;

    public SubProducts(String str) {
        if(str == null) {
            return;
        }
        JsonObject obj = new JsonObject(str);
        this.id = obj.getInteger("id", 0);
        this.productId = obj.getInteger("productId", 0);
        this.isCell = obj.getBoolean("isCell", false);
    }
    public String toString() {
        return new JsonObject()
                .put("id", this.id)
                .put("productId", this.productId)
                .put("isCell", this.isCell)
                .toString();
    }
    public JsonObject toJsonObject() {
        return new JsonObject()
                .put("id", this.id)
                .put("productId", this.productId)
                .put("isCell", this.isCell);
    }
}
