package com.dylansyardsale.repository;

import com.dylansyardsale.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

//REQUIRED for CRUD operations on Order entity
public interface OrderRepository extends JpaRepository<Order, Long> {}