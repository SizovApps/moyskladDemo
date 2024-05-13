package com.romashko.romashkoTestProject.services.Impl;

import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.models.ProductSell;
import com.romashko.romashkoTestProject.repositories.ProductSellRepository;
import com.romashko.romashkoTestProject.services.ProductSellService;
import com.romashko.romashkoTestProject.services.ProductsService;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class ProductSellServiceImpl implements ProductSellService {

    private final ProductSellRepository productSellRepository;
    private final ProductsService productsService;

    public ProductSellServiceImpl(ProductSellRepository productSellRepository, ProductsService productsService) {
        this.productSellRepository = productSellRepository;
        this.productsService = productsService;
    }
    @Override
    public List<ProductSell> getAllProductSells() {
        return productSellRepository.findAll();
    }

    @Override
    public ProductSell findProductSellById(Long id) {
        Optional<ProductSell> productSell = productSellRepository.findById(id);
        return productSell.orElse(null);
    }

    @Override
    public ProductSell createProductSell(ProductSell productSell) {
        Product sellingProduct = productSell.getProduct();
        if (sellingProduct.getAmountOfProduct() < productSell.getProductAmount()) {
            throw new IllegalArgumentException("AmountOfProduct is lower than you want to buy!");
        }
        countAmountOfProductLostAndFullPrice(productSell, productSell.getProductAmount());
        productsService.updateProduct(sellingProduct);
        return productSellRepository.save(productSell);
    }

    @Override
    public ProductSell updateProductSell(ProductSell productSell) {
        ProductSell oldProductSell = productSellRepository.findById(productSell.getId()).orElse(null);
        if (oldProductSell == null) {
            throw new IllegalArgumentException("Product with such id not found!");
        }
        Product sellingProduct = productSell.getProduct();
        int productAmountDifference =  productSell.getProductAmount() - oldProductSell.getProductAmount();
        if (sellingProduct.getAmountOfProduct() < productAmountDifference) {
            throw new IllegalArgumentException("AmountOfProduct is lower than you want to buy!");
        }

        countAmountOfProductLostAndFullPrice(productSell, productAmountDifference);
        productsService.updateProduct(sellingProduct);
        return productSellRepository.save(productSell);
    }

    @Override
    public void deleteProductSellById(Long id) {
        productSellRepository.deleteById(id);
    }

    public void countAmountOfProductLostAndFullPrice(ProductSell productSell, int productAmountDifference) {
        Float fullPrice = productSell.getProductAmount() * productSell.getProduct().getPrice();
        productSell.setFullPrice(fullPrice);

        Product sellingProduct = productSell.getProduct();
        sellingProduct.setAmountOfProduct(sellingProduct.getAmountOfProduct() - productAmountDifference);
        sellingProduct.setAvailable(sellingProduct.getAmountOfProduct() != 0);
    }
}
