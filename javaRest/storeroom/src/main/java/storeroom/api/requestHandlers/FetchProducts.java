package storeroom.api.requestHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storeroom.api.dataStore.LiveStore;
import storeroom.api.model.Product;
import storeroom.api.utils.RestUtils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FetchProducts implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Map<String, String> params = RestUtils.queryStringParamsToMap(t.getRequestURI().getQuery());

        String department = params.get("department");
        String category = params.get("category");
        String matchText = params.get("match"); 

        LiveStore store = LiveStore.getInstance();
//.........................................
        List<Product> products =  store.fetchProducts(department, category, matchText);
        JsonArray productJsonArray = mapProductsListToJsonArray(products);

        t.sendResponseHeaders(200, 0); 

        try (BufferedOutputStream out = new BufferedOutputStream(t.getResponseBody())) {
            byte [] data = productJsonArray.toString().getBytes();
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
                byte [] buffer = new byte [data.length];
                int count ;
                while ((count = bis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
            }
        }
    }

    private JsonArray mapProductsListToJsonArray(List<Product> products) {
    	//......................................................
        List<JsonObject> jsonProducts = products.stream().map(p -> p.toJsonObject()).collect(Collectors.toList());

        JsonArrayBuilder builder = Json.createArrayBuilder();
        jsonProducts.forEach(p ->  builder.add(p));
        return builder.build();
    }
}