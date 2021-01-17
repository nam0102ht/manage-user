package com.ntnn.model;

import io.vertx.core.json.JsonObject;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Persons {
    private Long id;
    private String firstName;
    private String lastName;
    private Long dob;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer roleId;
    private String extras;

    public Persons(String str) {
        JsonObject jo = new JsonObject(str);
        this.id = jo.getLong("id", 0L);
        this.firstName = jo.getString("firstName", "");
        this.lastName = jo.getString("lastName", "");
        this.dob = jo.getLong("dob", 0L);
        this.address = jo.getString("address", "");
        this.email = jo.getString("email", "");
        this.phoneNumber = jo.getString("phoneNumber", "");
        this.roleId = jo.getInteger("roleId", 2);
        this.extras = jo.getString("extras", "");
    }
}
