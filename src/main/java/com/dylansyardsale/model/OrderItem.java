package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;



@Entity // MUST - This class is the line-item entity that links one Order to one Product with a chosen quantity.
public class OrderItem {
	@Id // MUST - Declares this field as the primary key for uniquely identifying each order item row.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MUST - Uses database identity/auto-increment so the DB generates id values.
    private Long id; 
  

    @JsonIgnore // Prevents infinite JSON recursion when serializing Order -> items -> order -> ...
    @ManyToOne // MUST - Defines many-to-one relationship: many OrderItem rows can belong to one Order.
    private Order order; 
  

    @ManyToOne // Many order items can reference the same product.
    private Product product; 
   

    @Positive // Quantity must be greater than zero.
    private int quantity;

    public OrderItem() {} 
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