package com.dylansyardsale.controller;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.*;
import com.dylansyardsale.repository.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty; // ADDED NOTE: Validation for non-empty order items list
import jakarta.validation.constraints.NotNull;  // ADDED NOTE: Validation for required fields in DTO
import jakarta.validation.constraints.Positive; // ADDED NOTE: Validation for quantity > 0
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController //MUST-Marks this class as a REST API controller
@RequestMapping("/api/orders") //MUST-Defines the base URL for all end points in this controller, which will be /api/orders
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    //Added ProductRepository to look up products when building order items.
    private final ProductRepository productRepository;
    //Gives Spring the repositories this controller depends on.
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping //MUST-Gets all orders from the database and returns every order in the database.
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{id}") //MUST-Gets a single order by its ID, and returns the order if found.
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}") // Added a new filter endpoint so users only get items that match what they choose, instead of getting everything.
    public ResponseEntity<List<Order>> getByStatus(@PathVariable OrderStatus status) {
        List<Order> filtered = orderRepository.findAll().stream()
            .filter(o -> o.getStatus() == status)
            .toList();
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/{id}/items") // Uses OrderItemRepository to fetch all items belonging to a specific order.
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found: " + id);
        }
        List<OrderItem> items = orderItemRepository.findByOrderId(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping //MUST-Creates a new order in the database and returns the created order.
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) { //DELETE-COMPLEX ORDER CODE SECTION
        // Explicit guard to prevent creating empty orders
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        Order order = new Order();//Set the order date to the current date and time when the order is created, and set the status and packaging cost based on the request body.
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        //Logic to map Product IDs to specific OrderItems in the database
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));
            
            OrderItem item = new OrderItem();//Connect item to the selected product and back to the parent order
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemRequest.getQuantity()); // CHANGED NOTE: quantity now comes from request
            items.add(item);
        }
        
        order.setItems(items);

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}") //MUST-Updates an existing order by its ID, and returns the updated order if found. 
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order updated) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(updated.getStatus());
        order.setPackagingCost(updated.getPackagingCost());
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping("/{id}") //MUST-Deletes an order by its ID.
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found: " + id);
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

//Includes fields for the order status, packaging cost, and a list of items to be included in the order.  
class OrderRequest {
    @NotNull // Validation: status is required when creating an order.
    private OrderStatus status;
    private Double packagingCost;

    @NotEmpty // Enforces at least one order item
    private List<OrderItemRequest> items; //Replaced productIds with item DTO including quantity

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Double getPackagingCost() { return packagingCost; }
    public void setPackagingCost(Double packagingCost) { this.packagingCost = packagingCost; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}

// DTO to support quantity per product in order creation
class OrderItemRequest {
    @NotNull
    private Long productId;

    @NotNull  // Validation: quantity is required.
    @Positive 
    private Integer quantity;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}