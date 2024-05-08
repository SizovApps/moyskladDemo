package com.romashko.romashkoTestProject.repositories;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;

public interface ProductRepository {

    public List<Product> getAllProducts();

    public Product findProductByName(String name);

    public Product addProduct(Product product);

    public Product updateProduct(Product product);

    public void deleteProductByName(String name);
}
