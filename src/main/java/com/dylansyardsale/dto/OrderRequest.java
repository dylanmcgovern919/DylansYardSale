package com.dylansyardsale.dto;

import com.dylansyardsale.model.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

//Includes fields for the order status, packaging cost, and a list of items to be included in the order.  
public class OrderRequest {

    @NotNull(message = "status is required")
    private OrderStatus status;

    @NotNull(message = "packagingCost is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "packagingCost must be non-negative")
    private Double packagingCost;

    @NotEmpty(message = "items must not be empty") // Enforces at least one order item
    @Valid
    private List<OrderItemRequest> items; //Replaced productIds with item DTO including quantity

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}