package com.dylansyardsale.controller;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.*;
import com.dylansyardsale.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController //REQUIRED
@RequestMapping("/api/orders") //REQUIRED
public class OrderController {
    private final OrderRepository orderRepository;
    
    // ABOVE AND BEYOND PART: ADDED BY GOOGLE - Injecting ProductRepository to link inventory to shipping orders
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @GetMapping //REQUIRED
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{id}") //REQUIRED
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return ResponseEntity.ok(order);
    }

    @PostMapping //REQUIRED
    // ABOVE AND BEYOND PART: ADDED BY GOOGLE - Using OrderRequest DTO to handle complex list-based purchases
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        // ABOVE AND BEYOND PART: ADDED BY GOOGLE - Logic to map Product IDs to specific OrderItems in the database
        List<OrderItem> items = new ArrayList<>();
        for (Long productId : request.getProductIds()) {
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
            
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(1); 
            items.add(item);
        }
        
        order.setItems(items);

        // COPILOT NOTE: removed shipping QR assignment; order is identified by DB id
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}") //REQUIRED
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order updated) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(updated.getStatus());
        order.setPackagingCost(updated.getPackagingCost());
        // COPILOT NOTE: removed order.setQrCode(...) because using id only
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping("/{id}") //REQUIRED
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found: " + id);
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

// ABOVE AND BEYOND PART: ADDED BY GOOGLE - Request DTO for clean Swagger JSON input
class OrderRequest {
    // COPILOT NOTE: removed qrCode from request body (id-based flow now)
    private OrderStatus status;
    private Double packagingCost;
    private List<Long> productIds;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
}

