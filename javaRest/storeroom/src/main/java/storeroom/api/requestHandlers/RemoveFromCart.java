package storeroom.api.requestHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storeroom.api.dataStore.LiveStore;
import storeroom.api.model.Cart;
import storeroom.api.utils.Constants;
import storeroom.api.utils.RestUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class RemoveFromCart implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException { 
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase(Constants.POST)) {
            String requestBody = RestUtils.streamToString(exchange.getRequestBody());
            JsonObject object = RestUtils.stringJsonToJsonObject(requestBody);

            String cartId = object.getString("cartId");
            String productId = object.getString("productId");
            Integer quantity = object.getInt("quantity");



            JsonObject res;

            try {
                LiveStore store = LiveStore.getInstance();
                store.removeFromCart(cartId, productId, quantity);
                Cart cart =  store.getCartById(cartId);
                res = cart == null? null : cart.toJsonObject();
            }
            catch (Exception ex) {
                res = Json.createObjectBuilder()
                        .add("message", ex.getMessage())
                        .build();
            }

            exchange.getResponseHeaders().set(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
            exchange.sendResponseHeaders(200, 0);

            if(res == null) {
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(null);
                responseBody.close();
            } else {
                try (BufferedOutputStream out = new BufferedOutputStream(exchange.getResponseBody())) {
                    byte [] data = res.toString().getBytes();
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
                        byte [] buffer = new byte [data.length];
                        int count ;
                        while ((count = bis.read(buffer)) != -1) {
                            out.write(buffer, 0, count);
                        }
                    }
                }
            }

        } else {
            new RestUtils.NotImplementedHandler().handle(exchange);
        }
    }
}