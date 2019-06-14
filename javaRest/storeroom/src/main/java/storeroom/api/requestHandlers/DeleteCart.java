      package storeroom.api.requestHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storeroom.api.dataStore.LiveStore;
import storeroom.api.utils.Constants;
import storeroom.api.utils.RestUtils;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class DeleteCart implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase(Constants.POST)) {
            String requestBody = RestUtils.streamToString(exchange.getRequestBody());
            JsonObject object = RestUtils.stringJsonToJsonObject(requestBody);

            String cartId = object.getString("cartId");

            LiveStore store = LiveStore.getInstance();

            try {
                store.deleteCart(UUID.fromString(cartId));
            } catch (Exception ex) {
                // in case of invalid cart id does nothing
            }

            exchange.getResponseHeaders().set(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
            exchange.sendResponseHeaders(200, 0);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(null);
            responseBody.close();
        } else {
            new RestUtils.NotImplementedHandler().handle(exchange);
        }
    }
}