package com.dylansyardsale.repository;

import com.dylansyardsale.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//REQUIRED for CRUD operations on OrderItem
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Returns all OrderItems belonging to a given order ID.
    List<OrderItem> findByOrderId(Long orderId);
}

