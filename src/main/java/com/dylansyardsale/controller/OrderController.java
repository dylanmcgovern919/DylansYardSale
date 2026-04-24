package com.dylansyardsale.controller;

import com.dylansyardsale.dto.OrderRequest;
import com.dylansyardsale.dto.OrderResponse;
import com.dylansyardsale.model.OrderStatus;
import com.dylansyardsale.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//MUST-Marks this class as a REST API controller
@RestController
//MUST-Defines the base URL for all end points in this controller, which will be /api/orders
@RequestMapping("/api/orders")
public class OrderController {
    //Delegates all business logic to the order service layer.
    private final OrderService orderService;

    //Gives Spring the service this controller depends on.
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //MUST-Gets all orders from the database and returns every order in the database.
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    //MUST-Gets a single order by its ID, and returns the order if found.
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOne(id));
    }

    // Added a new filter endpoint so users only get items that match what they choose, instead of getting everything.
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getByStatus(@PathVariable String status) {
        OrderStatus parsedStatus;
        try {
            parsedStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        return ResponseEntity.ok(orderService.getByStatus(parsedStatus));
    }

    //MUST-Creates a new order in the database and returns the created order.
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    //MUST-Updates an existing order by its ID, and returns the updated order if found.
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @Valid @RequestBody OrderRequest updatedRequest) {
        return ResponseEntity.ok(orderService.update(id, updatedRequest));
    }

    //MUST-Deletes an order by its ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}