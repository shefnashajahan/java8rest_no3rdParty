package storeroom.api.dataStore;

import storeroom.api.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sun.tools.corba.se.idl.InvalidArgument;

public class LiveStore {

    private static LiveStore liveStore_instance = null;
    private Store store;
    private List<Cart> carts;

    private LiveStore() {
        this.store = InMemoryStore.createStore();
        this.carts = new ArrayList<Cart>();
    }

    private List<Product> getAllProductsByCategoryId(UUID categoryId) {
        List<Product> result = store.getProducts().stream()
                .filter(p -> p.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
        return result;
    }

    private List<Product> getAllProductsByDepartmentId(UUID departmentId) {
        List<Product> result = new ArrayList<Product>();

        List<Category> categories = getAllCategoriesByDepartmentId(departmentId);

        categories.forEach(c -> {
            List<Product> productsByCategory = getAllProductsByCategoryId(c.getId());
            result.addAll(productsByCategory);
        });

        return  result;
    }

    private List<Category> getAllCategoriesByDepartmentId(UUID departmentId) {
        List<Category> result = store.getCategories().stream()
                .filter(p -> p.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
        return result;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private Product getProductById(String productId) {
        UUID pId = UUID.fromString(productId);

        Optional<Product> findings = this.store.getProducts().stream()
                .filter(p -> p.getId().equals(pId)).findFirst();
        return findings.orElse(null);
    }

    private void updateCart(Cart cart) {
        UUID cartId = cart.getId();

        Cart existingCart = this.carts.stream().filter(x -> x.getId().equals(cartId)).findAny().orElse(null);

        if(existingCart == null) {
            this.carts.add(cart);
        } else {
            this.carts.stream().forEach(c -> {
                if(c.getId().equals(cart.getId())) {
                    c.products = cart.products;
                }
            });
        }
    }

    private Cart updateProductsInCart(Cart cart, CartProduct product){
        CartProduct existingProduct = cart.products.stream()
                .filter(p -> p.productId.equals(product.productId))
                .findAny().orElse(null);

        if(existingProduct == null) {
            cart.products.add(product);
        } else {
            cart.products.stream().forEach(p -> {
                if(p.productId.equals(product.productId)) {
                    p.quantity += product.quantity;
                    this.store.removeProduct(p.productId, product.quantity);
                }
            });
        }

        return cart;
    }

    private void revokeAllProductsFromCart(UUID cartId) throws Exception {
        Cart cart = getCartById(cartId.toString());
        if(cart == null) {
            return;
        }

        cart.products.forEach(p -> {
            this.store.addProduct(p.productId, p.quantity);
        });
    }

    public static LiveStore getInstance() {
        if (liveStore_instance == null)
            liveStore_instance = new LiveStore();

        return liveStore_instance;
    }

    public Store getStoreStatus() {
        return this.store;
    }

    public Cart getCartById(String cId) throws Exception {
        if (isNullOrEmpty(cId)) {
            throw new InvalidArgument("Invalid cart Id");
        }

        UUID cartId = UUID.fromString(cId);
        Optional<Cart> findings = this.carts.stream().filter(c -> c.id.equals(cartId)).findFirst();
        return findings.orElse(null);
    }

    public List<Product> fetchProducts(String departmentId, String categoryId, String match) {
        List<Product> result = new ArrayList<Product>();

        if(isNullOrEmpty(departmentId) && isNullOrEmpty(categoryId)) {
            result = store.getProducts();
        } else {
            if(!isNullOrEmpty(departmentId)){
                result.addAll(getAllProductsByDepartmentId(UUID.fromString(departmentId)));
            }

            if(!isNullOrEmpty(categoryId)){
                result.addAll(getAllProductsByCategoryId(UUID.fromString(categoryId)));
            }
        }

        if(!result.isEmpty() && !isNullOrEmpty(match)){
            result = result.stream()
                    .filter(p -> p.getName().toLowerCase().contains(match.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

    public UUID addToCart(String cId, String pId, Integer qty) throws Exception {
        Product product = getProductById(pId);
        if(product == null) {
            throw new InvalidArgument("Invalid Product Id");
        }

        if(product.getQuantity() < qty) {
            throw new InvalidArgument("Insufficient product request");
        }

        Cart cart =  isNullOrEmpty(cId) ? new Cart() : getCartById(cId);

        cart = updateProductsInCart(cart, new CartProduct(product.getId(), product.getName(), qty));

        updateCart(cart);

        store.removeProduct(product.getId(), qty);

        return cart.getId();
    }

    public void removeFromCart(String cId, String pId, Integer qty)  throws Exception {
        Cart cart = getCartById(cId);

        cart.products.stream().forEach(p -> {
            if(p.productId.equals(UUID.fromString(pId))) {
                if(p.quantity > qty) {
                    this.store.addProduct(UUID.fromString(pId), qty);
                    p.quantity -= qty;
                } else {
                    this.store.addProduct(UUID.fromString(pId), p.quantity);
                    p.quantity = 0;
                }
            }
        });

        cart.products = cart.products.stream().filter(p -> p.quantity > 0)
                .collect(Collectors.toList());

        if(cart.products.size() > 0) {
            updateCart(cart);
        } else {
            deleteCart(cart.getId());
        }
    }

    public void deleteCart(UUID cartId) throws Exception {
        revokeAllProductsFromCart(cartId);
        this.carts.removeIf(cart -> cart.getId().equals(cartId));
    }

}
