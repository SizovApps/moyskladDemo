package com.romashko.romashkoTestProject.services;

import com.romashko.romashkoTestProject.models.ProductSell;
import java.util.List;

public interface ProductSellService {

    List<ProductSell> getAllProductSells();

    ProductSell findProductSellById(Long id);

    ProductSell createProductSell(ProductSell productSell);

    ProductSell updateProductSell(ProductSell productSell);

    void deleteProductSellById(Long id);
}
