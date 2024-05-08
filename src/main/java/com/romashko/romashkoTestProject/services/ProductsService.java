package com.romashko.romashkoTestProject.services;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;

public interface ProductsService {

    public List<Product> getAllProducts();

    public Product findProductByName(String name);
    public Product findProductById(Long id);

    public Product createProduct(Product product);

    public Product updateProduct(Product product);

    public void deleteProductByName(String name);
}
