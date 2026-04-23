package com.dylansyardsale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

<<<<<<< HEAD
// Order is a required entity //REQUIRED - Core business entity representing a customer purchase transaction.
// Demonstrates one-to-many //REQUIRED - One Order can contain multiple OrderItem rows.
@Entity //REQUIRED - Marks this class as a JPA entity mapped to a database table.
@Table(name = "orders") //REQUIRED - Uses explicit table name to avoid reserved-word and naming issues.
public class Order {
    @Id //REQUIRED - Primary key for the orders table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED - Uses MySQL auto-increment identity strategy.
    private Long id; //REQUIRED - Unique database identifier for each order.

    private String qrCode; //REQUIRED - Demo shipping/tracking identifier shown in API responses.
    private LocalDateTime orderDate; // Timestamp of when the order was created.

    @PositiveOrZero // Validation: packaging cost cannot be negative.
    private Double packagingCost;

    @NotNull // Validation: every order must have a valid status.
    @Enumerated(EnumType.STRING) // Stores enum as readable string (PROCESSING/SHIPPED/COMPLETED).
    private OrderStatus status;

    // One-to-many: Order -> OrderItem //REQUIRED - Child rows belong to this parent order.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //REQUIRED - Persist/update/delete OrderItem rows with the parent Order.
    private List<OrderItem> items = new ArrayList<>();

    public Order() {} //REQUIRED - Required no-arg constructor for JPA.
=======

@Entity //MUST-Tells JPA this class maps to a database.
@Table(name = "orders") //MUST-Uses explicit table name to avoid reserved-word and naming issues.
public class Order {
    @Id //MUST-Primary key for the orders table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MUST-Auto-increments ID (MYSQL style).
    private Long id; //REQUIRED - Unique database identifier for each order.

    private LocalDateTime orderDate; // date/time of when the order was created.

    @PositiveOrZero // Extra cost of packaging materials, can't be < 0.
    private Double packagingCost;

    @NotNull //Every order must have a valid status.
    @Enumerated(EnumType.STRING) //Stores enum as readable string (PROCESSING/SHIPPED/COMPLETED).
    private OrderStatus status;

    //MUST-One-to-many relationship: Order - > OrderItems.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //MUST-If i save/delete an order, it also saves/deletes the associated order items.
    private List<OrderItem> items = new ArrayList<>();

    public Order() {} //MUST-Required no-arg constructor for JPA.
>>>>>>> 4ed9b39 (update from spring tools)

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