package storeroom.api.model;

import java.util.List;
import java.util.UUID;

public class Store {
    private List<Department> departments;

    private List<Category> categories;
    public List<Category> getCategories() { return this.categories; }

    private List<Product> products;
    public List<Product> getProducts() { return this.products; }

    public Store(List<Department> departments, List<Category> categories, List<Product> products)
    {
        this.departments = departments;
        this.categories = categories;
        this.products = products;
    }

    public void removeProduct(UUID productId, Integer qty) {
        this.products.stream().forEach(p -> {
            if(p.getId().equals(productId)) {
                p.setQuantity(p.getQuantity() - qty);
            }
        });
    }

    public void addProduct(UUID productId, Integer qty) {
        this.products.stream().forEach(p -> {
            if(p.getId().equals(productId)) {
                p.setQuantity(p.getQuantity() + qty);
            }
        });
    }
}
