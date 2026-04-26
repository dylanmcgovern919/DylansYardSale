package com.dylansyardsale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

 //- Core business entity representing a customer purchase transaction.
 //- One Order can contain multiple OrderItem rows.
@Entity // Marks this class as a JPA entity mapped to a database table.
@Table(name = "orders") // Uses explicit table name to avoid reserved-word and naming issues.
public class Order {
    @Id // Primary key for the orders table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses MySQL auto-increment identity strategy.
    private Long id; // Unique database identifier for each order.


    private LocalDateTime orderDate; // Timestamp of when the order was created.

    @PositiveOrZero // Validation: packaging cost cannot be negative.
    private Double packagingCost;

    @NotNull // Validation: every order must have a valid status.
    @Enumerated(EnumType.STRING) // Stores enum as readable string (PROCESSING/SHIPPED/COMPLETED).
    private OrderStatus status;

    // One-to-many: Order -> OrderItem // Child rows belong to this parent order.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Persist/update/delete OrderItem rows with the parent Order.
    private List<OrderItem> items = new ArrayList<>();

    public Order() {} // Required no-arg constructor for JPA.

    public Long getId() { return id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}

