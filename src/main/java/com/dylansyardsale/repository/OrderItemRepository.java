package com.dylansyardsale.repository;

import com.dylansyardsale.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

//REQUIRED for CRUD operations on OrderItem
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}

