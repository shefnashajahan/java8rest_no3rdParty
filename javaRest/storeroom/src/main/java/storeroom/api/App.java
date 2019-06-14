package storeroom.api;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import storeroom.api.requestHandlers.*;


/**
 * Product Rest API
 */
public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8022), 0);
        server.createContext("/products", new FetchProducts());
        server.createContext("/cart", new FetchCart());
        server.createContext("/addToCard", new AddToCart());
        server.createContext("/removeFromCart", new RemoveFromCart());
        server.createContext("/deleteCart", new DeleteCart());
        server.setExecutor(null); // creates a default executor
        server.start();
    } 
}

