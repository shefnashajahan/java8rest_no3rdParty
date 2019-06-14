 package storeroom.api;

import com.sun.tools.corba.se.idl.InvalidArgument;
import org.junit.Assert;
import org.junit.Test;
import storeroom.api.dataStore.LiveStore;
import storeroom.api.model.Cart;
import storeroom.api.model.Product;

import java.util.List;
import java.util.UUID;

/**
 * Unit test for live store.
 */
public class AppTest
{
    LiveStore liveStore = LiveStore.getInstance();

    /**
     * Tests if the store is getting initialised
     */
    @Test
    public void localStoreShouldBeInitialisedWithProductStocks()
    {
        int categoryCount = 9;
        int productCount = 24;
        Assert.assertEquals(liveStore.getStoreStatus().getProducts().size(), productCount);
        Assert.assertEquals(liveStore.getStoreStatus().getCategories().size(), categoryCount);
    }

    /**
     * Test if fetch products by department id returns right number of results
     */
    @Test
    public void fetchProductByDepartmentId()
    {
        String departmentId = "fcdd8a63-378d-44fa-b1f5-2dbd79fcccae";
        int expectedProductCount = 10;
        List<Product> products = liveStore.fetchProducts(departmentId, null, null);
        Assert.assertEquals(products.size(), expectedProductCount);
    }

    /**
     * Test if fetch products by category id returns right number of results
     */
    @Test
    public void fetchProductByCategoryId()
    {
        String categoryId = "b56ffe88-65d2-408f-a816-3fb7fc7f1e87";
        int expectedProductCount = 2;
        List<Product> products = liveStore.fetchProducts(null, categoryId, null);
        Assert.assertEquals(products.size(), expectedProductCount);
    }

    /**
     * Test if fetch products by match text returns matching results
     */
    @Test
    public void fetchProductByMatchText()
    {
        int expectedProductCount = 1;
        String matchText = "iphone";
        List<Product> products = liveStore.fetchProducts(null, null, matchText);
        Assert.assertEquals(products.size(), expectedProductCount);
        Assert.assertEquals(products.get(0).getName(), "iphoneX");

        expectedProductCount = 2;
        matchText = "galaxy";
        products = liveStore.fetchProducts(null, null, matchText);
        Assert.assertEquals(products.size(), expectedProductCount);
        Assert.assertEquals(products.get(0).getName(), "Galaxy S7");
        Assert.assertEquals(products.get(1).getName(), "Galaxy S8");
    }

    /**
     * Test the life cycle of a cart - add/remove/delete
     */
    @Test
    public void cartLifeCycleTest() throws Exception
    {
        // create a cart by adding a product
        String productId1 = "7be1654a-1315-4e2c-abd0-1f9a0a2687c3";
        UUID cId = liveStore.addToCart(null, productId1, 2);
        Cart cart = liveStore.getCartById(cId.toString());
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.products.size(), 1);
        Assert.assertEquals(cart.products.get(0).productId.toString(), productId1);
        Assert.assertEquals(cart.products.get(0).quantity, 2);

        // adding one more product to same cart
        String productId2 = "eba4150a-28c2-482c-ae06-8e877bd1af05";
        liveStore.addToCart(cId.toString(), productId2, 3);
        cart = liveStore.getCartById(cId.toString());
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.products.size(), 2);
        Assert.assertEquals(cart.products.get(1).productId.toString(), productId2);
        Assert.assertEquals(cart.products.get(1).quantity, 3);

        // delete a product from cart - just the quantity
        liveStore.removeFromCart(cId.toString(), productId2, 2);
        cart = liveStore.getCartById(cId.toString());
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.products.size(), 2);
        Assert.assertEquals(cart.products.get(1).productId.toString(), productId2);
        Assert.assertEquals(cart.products.get(1).quantity, 1); // here is the updated count

        // delete a product totally from the cart
        liveStore.removeFromCart(cId.toString(), productId2, 1);
        cart = liveStore.getCartById(cId.toString());
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.products.size(), 1); // only one product left
        Assert.assertEquals(cart.products.get(0).productId.toString(), productId1);
        Assert.assertEquals(cart.products.get(0).quantity, 2);

        //delete cart
        liveStore.deleteCart(cId);
        cart = liveStore.getCartById(cId.toString());
        Assert.assertNull(cart); // is null
    }

    /**
     * Test if the empty carts are auto removed
     */
    @Test
    public void emptyCartsAutoRemoval() throws Exception
    {
        // create a cart by adding a product
        String productId1 = "7be1654a-1315-4e2c-abd0-1f9a0a2687c3";
        UUID cId = liveStore.addToCart(null, productId1, 2);
        Cart cart = liveStore.getCartById(cId.toString());
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.products.size(), 1);
        Assert.assertEquals(cart.products.get(0).productId.toString(), productId1);
        Assert.assertEquals(cart.products.get(0).quantity, 2);

        // empty cart by remove the product
        liveStore.removeFromCart(cId.toString(), productId1, 2);

        cart = liveStore.getCartById(cId.toString());
        Assert.assertNull(cart); // cart is removed automatically
    }

    /**
     * Test if adding a product to cart deducts its stock count from the live store
     * and remove a cart product is adding the total stock count of the product
     */
    @Test
    public void addingToCartDeductsTheStockCountOfTheProduct() throws Exception
    {
        // create a cart by adding a product
        Product product = getStoreProductByName("iphoneX");
        Integer initialProductCount = product.getQuantity();
        Integer cartProductCount = 2;
        UUID cId = liveStore.addToCart(null, product.getId().toString(), cartProductCount);

        product = getStoreProductByName("iphoneX");
        Assert.assertNotEquals(product.getQuantity(), initialProductCount);
        Integer remainingProductStock = initialProductCount - cartProductCount;
        Assert.assertEquals(product.getQuantity(), remainingProductStock);

        liveStore.deleteCart(cId);
        product = getStoreProductByName("iphoneX");
        Assert.assertEquals(product.getQuantity(), initialProductCount);

    }

    private Product getStoreProductByName(String name) {
        List<Product> products = liveStore.fetchProducts(null, null, name);
        return products.size() == 0 ? null : products.get(0);
    }

    /**
     * Test if an invalid cart id returns null result
     */
    @Test(expected = NullPointerException.class)
    public void invalidCartIdReturnNull() throws Exception {
        Cart cart = liveStore.getCartById(UUID.randomUUID().toString());
        cart.toJsonObject();
    }

    /**
     * Test if a null/empty cart id throws exception
     */
    @Test(expected = InvalidArgument.class)
    public void emptyCartIdThrowsException() throws Exception {
        liveStore.getCartById(null);
    }

    /**
     * Test if a null/empty product id throws exception
     */
    @Test(expected = InvalidArgument.class)
    public void inValidProductIdThrowsException() throws Exception {
        liveStore.addToCart(null, UUID.randomUUID().toString(), 1);
    }
}
