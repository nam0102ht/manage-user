package com.ntnn.model;

import io.vertx.core.json.JsonObject;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private int id;
    private String name;
    private Long personId;
    private String extras;
    private String products;

    public Orders(String str) {
        if(str == null) {
            return;
        }
        JsonObject jo = new JsonObject(str);
        this.id = jo.getInteger("id", 0);
        this.name = jo.getString("name", null);
        this.personId = jo.getLong("personId", personId);
        this.extras = jo.getString("extras", null);
        this.products = jo.getString("products", null);
    }

    public String toString() {
        return new JsonObject()
                .put("id", this.id)
                .put("name", this.name)
                .put("personId", this.personId)
                .put("extras", this.extras)
                .put("products", this.products).toString();
    }

    public JsonObject toJsonObject() {
        return new JsonObject(this.toString());
    }


    public static final class OrdersBuilder {
        private Orders orders;

        private OrdersBuilder() {
            orders = new Orders();
        }

        public static OrdersBuilder anOrders() {
            return new OrdersBuilder();
        }

        public OrdersBuilder withId(int id) {
            orders.setId(id);
            return this;
        }

        public OrdersBuilder withName(String name) {
            orders.setName(name);
            return this;
        }

        public OrdersBuilder withPersonId(Long personId) {
            orders.setPersonId(personId);
            return this;
        }

        public OrdersBuilder withExtras(String extras) {
            orders.setExtras(extras);
            return this;
        }

        public OrdersBuilder withProducts(String products) {
            orders.setProducts(products);
            return this;
        }

        public OrdersBuilder but() {
            return anOrders().withId(orders.getId()).withName(orders.getName()).withPersonId(orders.getPersonId()).withExtras(orders.getExtras()).withProducts(orders.getProducts());
        }

        public Orders build() {
            return orders;
        }
    }
}
