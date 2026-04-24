package com.dylansyardsale.dto;

import com.dylansyardsale.model.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

//Includes fields for the order status, packaging cost, and a list of items to be included in the order.  
public class OrderRequest {
    private OrderStatus status;
    private Double packagingCost;

    @NotEmpty // Enforces at least one order item
    private List<OrderItemRequest> items; //Replaced productIds with item DTO including quantity

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
