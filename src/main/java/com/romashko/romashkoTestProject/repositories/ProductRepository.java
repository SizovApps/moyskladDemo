package com.romashko.romashkoTestProject.repositories;

import com.romashko.romashkoTestProject.models.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByName(String name);

    void deleteProductByName(String name);
}
