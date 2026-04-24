package com.dylansyardsale.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// DTO to support quantity per product in order creation
public class OrderItemRequest {
    @NotNull
    private Long productId;

    @Positive 
    private Integer quantity;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
