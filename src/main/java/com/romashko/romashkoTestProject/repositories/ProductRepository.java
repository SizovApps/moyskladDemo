package com.romashko.romashkoTestProject.repositories;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findProductByName(String name);

    public void deleteProductByName(String name);
}
