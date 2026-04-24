package com.dylansyardsale.repository;

import com.dylansyardsale.model.Order;
import com.dylansyardsale.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//REQUIRED for CRUD operations on Order entity
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}