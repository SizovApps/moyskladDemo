package com.romashko.romashkoTestProject.services;


import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    public Product createProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public void deleteProductByName(String name) {
        productRepository.deleteProductByName(name);
    }
}
