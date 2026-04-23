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

@RestController //MUST-Marks this class as a REST API controller
@RequestMapping("/api/orders") //MUST-Defines the base URL for all end points in this controller, which will be /api/orders
public class OrderController {
    private final OrderRepository orderRepository;
    
    //Added ProductRepository to look up products when building order items.
    private final ProductRepository productRepository;
    //Gives Spring the repositories this controller depends on.
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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

    @PostMapping //MUST-Creates a new order in the database and returns the created order.
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) { //DELETE-COMPLEX ORDER CODE SECTION
        Order order = new Order();//Set the order date to the current date and time when the order is created, and set the status and packaging cost based on the request body.
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        //Logic to map Product IDs to specific OrderItems in the database
        List<OrderItem> items = new ArrayList<>();
        for (Long productId : request.getProductIds()) {
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
            
            OrderItem item = new OrderItem();//Connect item to the selected product and back to the parent order, and default quantity of 1 item
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(1); 
            items.add(item);
        }
        
        order.setItems(items);

<<<<<<< HEAD
        // COPILOT NOTE: removed shipping QR assignment; order is identified by DB id
=======
       
>>>>>>> 4ed9b39 (update from spring tools)
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}") //MUST-Updates an existing order by its ID, and returns the updated order if found. 
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order updated) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(updated.getStatus());
        order.setPackagingCost(updated.getPackagingCost());
<<<<<<< HEAD
        // COPILOT NOTE: removed order.setQrCode(...) because using id only
=======
>>>>>>> 4ed9b39 (update from spring tools)
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

//Includes fields for the order status, packaging cost, and a list of product IDs to be included in the order.  
class OrderRequest {
<<<<<<< HEAD
    // COPILOT NOTE: removed qrCode from request body (id-based flow now)
=======
>>>>>>> 4ed9b39 (update from spring tools)
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
