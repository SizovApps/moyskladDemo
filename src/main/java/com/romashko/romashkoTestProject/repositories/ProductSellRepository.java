package com.romashko.romashkoTestProject.repositories;

import com.romashko.romashkoTestProject.models.Product;
import com.romashko.romashkoTestProject.models.ProductSell;
import com.romashko.romashkoTestProject.models.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSellRepository extends JpaRepository<ProductSell, Long>, JpaSpecificationExecutor<ProductSell> {

}
