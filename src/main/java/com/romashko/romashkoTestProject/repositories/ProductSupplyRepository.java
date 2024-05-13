package com.romashko.romashkoTestProject.repositories;

import com.romashko.romashkoTestProject.models.ProductSupply;
import com.romashko.romashkoTestProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Long>, JpaSpecificationExecutor<Product> {

}
