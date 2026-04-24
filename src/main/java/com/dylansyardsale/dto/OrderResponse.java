package com.dylansyardsale.dto;

import com.dylansyardsale.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

// Response DTO for Order — avoids returning entities directly and prevents lazy serialization issues.
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Double packagingCost;
    private List<OrderItemResponse> items;

    public OrderResponse() {}

    public OrderResponse(Long id, LocalDateTime orderDate, OrderStatus status,
                         Double packagingCost, List<OrderItemResponse> items) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.packagingCost = packagingCost;
        this.items = items;
    }

    public Long getId() { return id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }
    public Double getPackagingCost() { return packagingCost; }
    public List<OrderItemResponse> getItems() { return items; }
}
