package storeroom.api.dataStore;

import storeroom.api.model.Category;
import storeroom.api.model.Department;
import storeroom.api.model.Product;
import storeroom.api.model.Store;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class InMemoryStore {

    public static Store createStore () {

        //Departments
        //###################################################################################

        Department electronics = new Department(UUID.fromString("fcdd8a63-378d-44fa-b1f5-2dbd79fcccae"), "Electronics");
        Department healthCare = new Department(UUID.fromString("c3ea613e-1017-4f1c-8770-ea68a9cfd186"), "Health care");
        Department grocery = new Department(UUID.fromString("9661e663-7f92-4517-8514-032129159fca"), "Grocery");

        List<Department> departments = asList(electronics, healthCare, grocery);


        //Categories
        //###################################################################################

        Category mobile = new Category(UUID.fromString("fb9394ac-6a73-4034-a125-4aeed82fec8c"), electronics.getId(), "Mobile");
        Category speaker = new Category(UUID.fromString("17145f20-7b0f-4e15-bc34-c7d13c0b41d4"), electronics.getId(), "Speaker");
        Category laptop = new Category(UUID.fromString("5a3ac360-b2bb-4fe4-b728-72d1e4a30e24"), electronics.getId(), "Laptop");

        Category hairCare = new Category(UUID.fromString("7eedbd5b-0341-4351-b3d1-9504b37c71a5"), healthCare.getId(), "Hair care");
        Category skinCare = new Category(UUID.fromString("4126c4c2-5764-465d-b0a2-a6ae4b3e5681"), healthCare.getId(), "Skin care");
        Category vitamins = new Category(UUID.fromString("b56ffe88-65d2-408f-a816-3fb7fc7f1e87"), healthCare.getId(), "Vitamins");

        Category dryFruits = new Category(UUID.fromString("dc2c8ec5-5ddd-47ba-b0ae-12ddf8b71c74"), grocery.getId(), "Dry Fruits");
        Category oils = new Category(UUID.fromString("9ec293f1-d124-4d20-9187-cbbf67cdf708"), grocery.getId(), "Oils");
        Category bakery = new Category(UUID.fromString("84c0e40d-3fd1-422f-814c-6e978f8afeca"), grocery.getId(), "Bakery");

        List<Category> categories = asList(mobile, speaker, laptop, hairCare, skinCare, vitamins,
                dryFruits, oils, bakery);

        //Products
        //###################################################################################

        Product galaxys7 = new Product.Builder(UUID.fromString("737e8805-600b-480c-a359-d012e906c60f"), mobile.getId())
                .withName("Galaxy S7")
                .atPrice(400)
                .withQuantity(5000)
                .build();

        Product galaxys8 = new Product.Builder(UUID.fromString("7215dc94-3482-4f2a-a85d-9865cb0b8fca"), mobile.getId())
                .withName("Galaxy S8")
                .atPrice(900)
                .withQuantity(9800)
                .build();

        Product iphoneX = new Product.Builder(UUID.fromString("8fb66b4a-be3d-4b54-9432-cdf6acbaab0b"), mobile.getId())
                .withName("iphoneX")
                .atPrice(700)
                .withQuantity(2000)
                .build();

        Product samsungLaptop = new Product.Builder(UUID.fromString("10e2b8d8-a874-4b93-a59a-ee5934c4adb5"), laptop.getId())
                .withName("Samsung laptop")
                .atPrice(400)
                .withQuantity(5000)
                .build();

        Product hpLaptop = new Product.Builder(UUID.fromString("7be1654a-1315-4e2c-abd0-1f9a0a2687c3"), laptop.getId())
                .withName("HP laptop")
                .atPrice(900)
                .withQuantity(9800)
                .build();

        Product appleLaptop = new Product.Builder(UUID.fromString("9049f038-4a09-41a1-a589-78f5c61444eb"), laptop.getId())
                .withName("Apple laptop - mac book")
                .atPrice(32)
                .withQuantity(2000)
                .build();

        Product sonyHeadset = new Product.Builder(UUID.fromString("eba4150a-28c2-482c-ae06-8e877bd1af05"), speaker.getId())
                .withName("Sony headSet")
                .atPrice(33)
                .withQuantity(7878)
                .build();

        Product jabraSpeaker = new Product.Builder(UUID.fromString("2fa7cb8b-e07d-44ea-9941-426905118ff1"), speaker.getId())
                .withName("Jabra speaker")
                .atPrice(32)
                .withQuantity(2332)
                .build();

        Product beats = new Product.Builder(UUID.fromString("8cb6712c-4046-4434-b02d-fa4ab2d5ec9a"), speaker.getId())
                .withName("Beats")
                .atPrice(120)
                .withQuantity(212)
                .build();

        Product boss = new Product.Builder(UUID.fromString("23d81113-0ebd-4b0a-8c09-29c206579995"), speaker.getId())
                .withName("Boss")
                .atPrice(134)
                .withQuantity(450)
                .build();

        List<Product> electronicProducts = asList(galaxys7, galaxys8, iphoneX, samsungLaptop, hpLaptop, appleLaptop,
                sonyHeadset, jabraSpeaker, beats, boss);

        Product doveShampoo = new Product.Builder(UUID.fromString("ac280929-b71d-4bfd-a7a0-0cacd84a7819"), hairCare.getId())
                .withName("Dove shampoo")
                .atPrice(20)
                .withQuantity(1000)
                .build();

        Product tressemeConditioner = new Product.Builder(UUID.fromString("ac3f85ec-36b9-4119-86d0-f2c980c629fa"), hairCare.getId())
                .withName("Tresseme Conditioner")
                .atPrice(40)
                .withQuantity(400)
                .build();

        Product oliveCream = new Product.Builder(UUID.fromString("e2cc0c64-1c08-447c-8f29-bd3005735844"), skinCare.getId())
                .withName("Olive moisture cream")
                .atPrice(40)
                .withQuantity(400)
                .build();

        Product pondsWhiteningCream = new Product.Builder(UUID.fromString("b21b53a7-cb6c-42da-ae31-9adffd446502"), skinCare.getId())
                .withName("Ponds whitening cream")
                .atPrice(5)
                .withQuantity(200)
                .build();

        Product omegaTablets = new Product.Builder(UUID.fromString("faf6d114-634e-4913-83f7-77b4487ccb76"), vitamins.getId())
                .withName("Omega 3 tablets")
                .atPrice(5)
                .withQuantity(200)
                .build();

        Product multiVitaminTablets = new Product.Builder(UUID.fromString("c11f1ced-307d-4a5b-ae0a-75d645cc3616"), vitamins.getId())
                .withName("Multi vitamin tablets")
                .atPrice(5)
                .withQuantity(200)
                .build();

        List<Product> healthCareProducts = asList(doveShampoo, tressemeConditioner, oliveCream, pondsWhiteningCream,
                omegaTablets, multiVitaminTablets);

        Product badam = new Product.Builder(UUID.fromString("185541c4-6827-4a91-862e-42443153f382"), dryFruits.getId())
                .withName("Badam 200g")
                .atPrice(5)
                .withQuantity(200)
                .build();

        Product pistachio = new Product.Builder(UUID.fromString("01a8d6ce-d751-40d8-8857-d78239909eb6"), dryFruits.getId())
                .withName("Pistachio 200g")
                .atPrice(5)
                .withQuantity(200)
                .build();

        Product dates = new Product.Builder(UUID.fromString("f5ea74d8-6414-43cd-a1dc-842682c44b2c"), dryFruits.getId())
                .withName("Dates 200g")
                .atPrice(5)
                .withQuantity(200)
                .build();

        Product oliveOil = new Product.Builder(UUID.fromString("08d6bad0-ccc9-4742-ba18-cb120194cfab"), oils.getId())
                .withName("Olive oil 500g")
                .atPrice(3)
                .withQuantity(400)
                .build();

        Product sunflowerOil = new Product.Builder(UUID.fromString("9b11fd85-b4f3-4680-be19-8191804c39c3"), oils.getId())
                .withName("Sunflower oil 500g")
                .atPrice(6)
                .withQuantity(400)
                .build();

        Product longBread = new Product.Builder(UUID.fromString("5f5d7cdd-e744-4653-8a71-09ec6696cf3e"), bakery.getId())
                .withName("Long bread")
                .atPrice(1)
                .withQuantity(50)
                .build();

        Product muffins = new Product.Builder(UUID.fromString("f95050b1-e10a-4d84-a194-73de8f6883e6"), bakery.getId())
                .withName("Muffins")
                .atPrice(3)
                .withQuantity(50)
                .build();

        Product croissant = new Product.Builder(UUID.fromString("cdfb90e3-d145-4474-a2c3-80ed568aed9c"), bakery.getId())
                .withName("Croissant")
                .atPrice(2)
                .withQuantity(100)
                .build();

        List<Product> groceryProducts = asList(badam, dates, pistachio, oliveOil, sunflowerOil,
                longBread, muffins, croissant);

        List<Product> allProducts = Stream.of(groceryProducts, electronicProducts, healthCareProducts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new Store(departments, categories, allProducts);

    }
}

