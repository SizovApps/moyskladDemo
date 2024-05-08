package com.romashko.romashkoTestProject.repositories;


import com.romashko.romashkoTestProject.models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductDAO {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public Product findProductByName(String name) {
        return products.stream()
                .filter(element -> element.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    public Product updateProduct(Product product) {
        int product_index = IntStream.range(0, products.size())
                .filter(index -> products.get(index).getName().equals(product.getName()))
                .findFirst()
                .orElse(-1);
        if (product_index > -1) {
            products.set(product_index, product);
            return product;
        }
        return null;
    }

    public void deleteProductByName(String name) {
        Product product = findProductByName(name);
        if (product != null) {
            products.remove(product);
        }
    }
}
