package com.ntnn.model;

import io.vertx.core.json.JsonObject;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Integer id;
    private String name;
    private String decription;
    private Long personId;

    public Department(String str) {
        JsonObject jo = new JsonObject(str);
        this.id = jo.getInteger("id", 0);
        this.name = jo.getString("name", "");
        this.decription = jo.getString("decription", "");
        this.personId = jo.getLong("personId", 0L);
    }

    public String toString(){
        return new JsonObject()
                .put("id", this.id)
                .put("name", this.name)
                .put("decription", this.decription)
                .put("personId", this.personId).toString();
    }
}
