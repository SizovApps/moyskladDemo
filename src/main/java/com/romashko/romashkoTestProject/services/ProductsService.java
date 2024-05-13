package com.romashko.romashkoTestProject.services;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;
import java.util.Map;

public interface ProductsService {

    List<Product> getAllProducts();

    Product findProductByName(String name);

    List<Product> filterProducts(Map<String, String> parameters);
    Product findProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductId(Long id);

}
