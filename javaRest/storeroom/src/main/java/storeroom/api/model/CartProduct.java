package storeroom.api.model;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.UUID;

public class CartProduct {
    public UUID productId;
    public int quantity;
    public String name;

    public CartProduct(UUID productId, String name,Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add("productId", this.productId.toString())
                .add("name", this.name)
                .add("quantity", this.quantity)
                .build();
    }
}
