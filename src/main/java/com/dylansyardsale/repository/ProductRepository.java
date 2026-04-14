package com.dylansyardsale.repository;

import com.dylansyardsale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // //ADDED BY GOOGLE

//REQUIRED for CRUD operations on Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // //ADDED BY GOOGLE: This allows searching by the 4-character mix
    Optional<Product> findByQrCode(String qrCode);
}
