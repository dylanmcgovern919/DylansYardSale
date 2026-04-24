package com.dylansyardsale.dto;

// Response DTO for OrderItem — includes product info needed by API consumers without circular entity references.
public class OrderItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;

    public OrderItemResponse() {}

    public OrderItemResponse(Long id, Long productId, String productName, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
}
