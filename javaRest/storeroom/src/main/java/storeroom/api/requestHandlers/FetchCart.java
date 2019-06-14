package storeroom.api.requestHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storeroom.api.dataStore.LiveStore;
import storeroom.api.model.Cart;
import storeroom.api.utils.RestUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class FetchCart implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Map<String, String> params = RestUtils.queryStringParamsToMap(t.getRequestURI().getQuery());

        String cartId = params.get("cartId");

        LiveStore store = LiveStore.getInstance();

        JsonObject res;

        try {
            Cart cart =  store.getCartById(cartId);
            res = cart.toJsonObject();
        }
        catch (Exception ex) {
            res = Json.createObjectBuilder()
                    .add("message", ex.getMessage())
                    .build();
        }


        t.sendResponseHeaders(200, 0);

        try (BufferedOutputStream out = new BufferedOutputStream(t.getResponseBody())) {
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

}
