package storeroom.api.model;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.UUID;

public class Product {
    private UUID id;
    public UUID getId() { return this.id; }

    private UUID categoryId;
    public UUID getCategoryId() { return this.categoryId; }

    private String name;
    public String getName() { return this.name; }

    private Integer costPerUnit;

    private Integer quantity;
    public Integer getQuantity() { return this.quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }


    public static class Builder {
        private UUID id;
        private UUID categoryId;
        private String name;
        private Integer quantity;
        private Integer costPerUnit;

        public Builder(UUID id, UUID categoryId) {
            this.id = id;
            this.categoryId = categoryId;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder atPrice(Integer costPerUnit){
            this.costPerUnit = costPerUnit;
            return this;
        }

        public Builder withQuantity(Integer quantity){
            this.quantity = quantity;
            return this;
        }

        public Product build(){
            Product product = new Product();
            product.id = this.id;
            product.categoryId = this.categoryId;
            product.name = this.name;
            product.costPerUnit = this.costPerUnit;
            product.quantity = this.quantity;
            return product;
        }
    }

    private Product() {

    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add("id", this.id.toString())
                .add("name", this.name)
                .add("cost", this.costPerUnit)
                .add("quantity", this.quantity)
                .build();
    }
}