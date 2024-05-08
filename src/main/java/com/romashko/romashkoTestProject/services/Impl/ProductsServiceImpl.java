package com.romashko.romashkoTestProject.services.Impl;

import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.repositories.ProductRepository;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);

    }

    @Override
    public void deleteProductByName(String name) {
        productRepository.deleteProductByName(name);
    }
}
