package storeroom.api.model;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Cart {
    public Cart() {
        this.id = UUID.randomUUID();
        this.products = new ArrayList<CartProduct>();
    }

    public UUID id;
    public List<CartProduct> products;

    public UUID getId () {
        return this.id;
    }

    private JsonArray jsonProductArray() {
        List<JsonObject> jsonProducts = products.stream().map(p -> p.toJsonObject())
                .collect(Collectors.toList());

        JsonArrayBuilder builder = Json.createArrayBuilder();
        jsonProducts.forEach(p ->  builder.add(p));
        return builder.build();
    }

    public JsonObject toJsonObject() {
       return Json.createObjectBuilder()
               .add("id", this.id.toString())
               .add("products", jsonProductArray())
               .build();
    }
}
