package com.ntnn.model;

import io.vertx.core.json.JsonObject;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    private int id;
    private String name;
    private int colorId;
    private String colorName;
    private double price;
    private int brandId;
    private String brandName;
    private String description;

    public Products(String str){
        if(str == null) return;
        JsonObject jo = new JsonObject(str);
        this.id = jo.getInteger("id", 0);
        this.name = jo.getString("name", null);
        this.colorId = jo.getInteger("colorId", 0);
        this.colorName = jo.getString("colorName", null);
        this.price = jo.getDouble("price", null);
        this.brandId = jo.getInteger("brandId", 0);
        this.brandName = jo.getString("brandName", null);
        this.description = jo.getString("description", null);
    }

    public String toString() {
        return new JsonObject()
                .put("id", this.id)
                .put("name", this.name)
                .put("colorId", this.colorId)
                .put("colorName", this.colorName)
                .put("price", this.price)
                .put("branId", this.brandId)
                .put("brandName", this.brandName)
                .put("description", this.description).toString();
    }

    public JsonObject toJsonObject() {
        return new JsonObject(this.toString());
    }

    public static final class ProductsBuilder {
        private Products products;

        private ProductsBuilder() {
            products = new Products();
        }

        public static ProductsBuilder aProducts() {
            return new ProductsBuilder();
        }

        public ProductsBuilder withId(int id) {
            products.setId(id);
            return this;
        }

        public ProductsBuilder withName(String name) {
            products.setName(name);
            return this;
        }

        public ProductsBuilder withColorId(int colorId) {
            products.setColorId(colorId);
            return this;
        }

        public ProductsBuilder withPrice(double price) {
            products.setPrice(price);
            return this;
        }

        public ProductsBuilder withBrandId(int brandId) {
            products.setBrandId(brandId);
            return this;
        }

        public ProductsBuilder withDescription(String description) {
            products.setDescription(description);
            return this;
        }

        public ProductsBuilder but() {
            return aProducts().withId(products.getId()).withName(products.getName()).withColorId(products.getColorId()).withPrice(products.getPrice()).withBrandId(products.getBrandId()).withDescription(products.getDescription());
        }

        public Products build() {
            return products;
        }
    }
}
