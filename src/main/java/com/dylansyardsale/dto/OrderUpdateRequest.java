package com.dylansyardsale.dto;

import com.dylansyardsale.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class OrderUpdateRequest {

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @PositiveOrZero(message = "Packaging cost must be zero or positive")
    private Double packagingCost;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
}
