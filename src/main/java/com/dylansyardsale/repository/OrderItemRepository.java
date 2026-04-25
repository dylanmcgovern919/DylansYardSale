package com.dylansyardsale.repository;

import com.dylansyardsale.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// for CRUD operations on OrderItem
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Finds all items that belong to one order.
    List<OrderItem> findByOrderId(Long orderId);

    // Finds one item by item id scoped to one order.
    Optional<OrderItem> findByIdAndOrderId(Long id, Long orderId);
}
