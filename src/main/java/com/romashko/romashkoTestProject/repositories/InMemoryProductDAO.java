package com.romashko.romashkoTestProject.repositories;


import com.romashko.romashkoTestProject.models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductDAO implements ProductRepository{

    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product findProductByName(String name) {
        return products.stream()
                .filter(element -> element.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
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

    @Override
    public void deleteProductByName(String name) {
        Product product = findProductByName(name);
        if (product != null) {
            products.remove(product);
        }
    }
}
