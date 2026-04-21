package com.dylansyardsale.repository;

import com.dylansyardsale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//REQUIRED for CRUD operations on Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {
    //removed findByQrCode go by ID
}
