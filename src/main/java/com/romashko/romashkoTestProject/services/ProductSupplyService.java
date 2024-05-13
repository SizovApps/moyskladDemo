package com.romashko.romashkoTestProject.services;

import com.romashko.romashkoTestProject.models.ProductSupply;
import java.util.List;

public interface ProductSupplyService {

    List<ProductSupply> getAllProductSupplies();

    ProductSupply findProductSupplyById(Long id);

    ProductSupply createProductSupply(ProductSupply productSupply);

    ProductSupply updateProductSupply(ProductSupply productSupply);

    void deleteProductSupplyById(Long id);
}
