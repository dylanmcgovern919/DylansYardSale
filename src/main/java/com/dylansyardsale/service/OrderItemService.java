package com.dylansyardsale.service;

import com.dylansyardsale.dto.OrderItemRequest;
import com.dylansyardsale.dto.OrderItemResponse;
import java.util.List;

// Declares business operations for OrderItem CRUD under an Order.
public interface OrderItemService {
    List<OrderItemResponse> getAllByOrder(Long orderId);
    OrderItemResponse getOne(Long orderId, Long itemId);
    OrderItemResponse create(Long orderId, OrderItemRequest request);
    OrderItemResponse update(Long orderId, Long itemId, OrderItemRequest request);
    void delete(Long orderId, Long itemId);
}
