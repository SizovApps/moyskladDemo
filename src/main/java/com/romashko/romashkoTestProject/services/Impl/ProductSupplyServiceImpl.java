package com.romashko.romashkoTestProject.services.Impl;


import com.romashko.romashkoTestProject.models.ProductSupply;
import com.romashko.romashkoTestProject.repositories.ProductSupplyRepository;
import com.romashko.romashkoTestProject.services.ProductSupplyService;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private final ProductSupplyRepository productSupplyRepository;

    public ProductSupplyServiceImpl(ProductSupplyRepository productSupplyRepository) {
        this.productSupplyRepository = productSupplyRepository;
    }

    @Override
    public List<ProductSupply> getAllProductSupplies() {
        return productSupplyRepository.findAll();
    }

    @Override
    public ProductSupply findProductSupplyById(Long id) {
        Optional<ProductSupply> productSupply = productSupplyRepository.findById(id);
        return productSupply.orElse(null);
    }

    @Override
    public ProductSupply createProductSupply(ProductSupply productSupply) {
        return productSupplyRepository.save(productSupply);
    }

    @Override
    public ProductSupply updateProductSupply(ProductSupply productSupply) {
        ProductSupply oldProductSupply = productSupplyRepository.findById(productSupply.getId()).orElse(null);
        if (oldProductSupply == null) {
            throw new IllegalArgumentException("Product with such id not found!");
        }
        return productSupplyRepository.save(productSupply);
    }

    @Override
    public void deleteProductSupplyById(Long id) {
        productSupplyRepository.deleteById(id);
    }
}
