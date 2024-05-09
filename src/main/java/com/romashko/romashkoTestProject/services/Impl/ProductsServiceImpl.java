package com.romashko.romashkoTestProject.services.Impl;

import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.repositories.ProductRepository;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.util.List;
import java.util.Optional;
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
        return productRepository.findProductByName(name).stream().findFirst().orElse(null);
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product old_product = productRepository.findById(product.getId()).orElse(null);
        if (old_product == null) {
            throw new IllegalArgumentException("Product with such id not found!");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProductId(Long id) {
        productRepository.deleteById(id);
    }
}
