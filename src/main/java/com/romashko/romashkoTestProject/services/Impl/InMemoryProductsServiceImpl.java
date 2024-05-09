package com.romashko.romashkoTestProject.services.Impl;


import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.repositories.InMemoryProductDAO;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMemoryProductsServiceImpl implements ProductsService {

    @Autowired
    private InMemoryProductDAO productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public Product findProductById(Long id) {
        throw new UnsupportedOperationException("Method findProductById(Long id) is not implemented yet");
    }

    public Product createProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    public void deleteProductId(Long id) {
        throw new UnsupportedOperationException("Method deleteProductId(Long id) is not implemented yet");
    }
}
