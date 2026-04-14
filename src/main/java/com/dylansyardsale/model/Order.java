package com.dylansyardsale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

// Order is a required entity //REQUIRED
// Demonstrates one-to-many //REQUIRED
@Entity //REQUIRED
@Table(name = "orders") //REQUIRED
public class Order {
    @Id //REQUIRED
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED
    private Long id; //REQUIRED

    private String qrCode; // Fake value for demo //REQUIRED
    private LocalDateTime orderDate;

    @PositiveOrZero
    private Double packagingCost;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // One-to-many: Order -> OrderItem //REQUIRED
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //REQUIRED
    private List<OrderItem> items = new ArrayList<>();

    public Order() {} //REQUIRED

    public Long getId() { return id; }
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
