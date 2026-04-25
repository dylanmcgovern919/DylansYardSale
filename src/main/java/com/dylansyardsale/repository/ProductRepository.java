package com.dylansyardsale.repository;

import com.dylansyardsale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// for CRUD operations on Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {

}
