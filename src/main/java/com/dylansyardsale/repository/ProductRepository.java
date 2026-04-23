package com.dylansyardsale.repository;

import com.dylansyardsale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//REQUIRED for CRUD operations on Product entity
public interface ProductRepository extends JpaRepository<Product, Long> {
<<<<<<< HEAD
    //removed findByQrCode go by ID
}
=======
  
}
>>>>>>> 4ed9b39 (update from spring tools)
