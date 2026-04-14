package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

// OrderItem is required for One-to-Many and Many-to-Many support //REQUIRED
@Entity //REQUIRED
public class OrderItem {
    @Id //REQUIRED
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED
    private Long id; //REQUIRED

    @JsonIgnore
    @ManyToOne //REQUIRED
    private Order order; //REQUIRED

    @ManyToOne
    private Product product; //REQUIRED

    @Positive
    private int quantity;

    public OrderItem() {} //REQUIRED
    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
