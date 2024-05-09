package com.romashko.romashkoTestProject.services;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;

public interface ProductsService {

    List<Product> getAllProducts();

    Product findProductByName(String name);
    Product findProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductId(Long id);

}
