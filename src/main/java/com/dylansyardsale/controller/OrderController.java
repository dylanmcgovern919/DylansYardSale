package com.dylansyardsale.controller;

import com.dylansyardsale.dto.OrderItemRequest;
import com.dylansyardsale.dto.OrderRequest;
import com.dylansyardsale.dto.OrderUpdateRequest;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Order;
import com.dylansyardsale.model.OrderItem;
import com.dylansyardsale.model.OrderStatus;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.repository.OrderRepository;
import com.dylansyardsale.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController //MUST-Marks this class as a REST API controller
@RequestMapping("/api/orders") //MUST-Defines the base URL for all endpoints in this controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @GetMapping //MUST-Gets all orders from the database.
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/{id}") //MUST-Gets a single order by its ID.
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}") //Filters orders by status using a DB-level query.
    public ResponseEntity<List<Order>> getByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderRepository.findByStatus(status));
    }

    @PostMapping //MUST-Creates a new order in the database.
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemRequest.getQuantity());
            items.add(item);
        }

        order.setItems(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(order));
    }

    @PutMapping("/{id}") //MUST-Updates status and packaging cost of an existing order.
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequest request) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());
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
