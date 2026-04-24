package com.dylansyardsale.controller;

import com.dylansyardsale.dto.OrderRequest;
import com.dylansyardsale.dto.OrderResponse;
import com.dylansyardsale.model.Order;
import com.dylansyardsale.model.OrderStatus;
import com.dylansyardsale.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //MUST-Marks this class as a REST API controller
@RequestMapping("/api/orders") //MUST-Defines the base URL for all end points in this controller, which will be /api/orders
public class OrderController {
    private final OrderService orderService; //Delegates all business logic to the order service layer.

    //Gives Spring the service this controller depends on.
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping //MUST-Gets all orders from the database and returns every order in the database.
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}") //MUST-Gets a single order by its ID, and returns the order if found.
    public ResponseEntity<OrderResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOne(id));
    }

    @GetMapping("/status/{status}") // Added a new filter endpoint so users only get items that match what they choose, instead of getting everything.
    public ResponseEntity<List<OrderResponse>> getByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getByStatus(status));
    }

    @PostMapping //MUST-Creates a new order in the database and returns the created order.
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) { //DELETE-COMPLEX ORDER CODE SECTION
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PutMapping("/{id}") //MUST-Updates an existing order by its ID, and returns the updated order if found. 
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @Valid @RequestBody Order updated) {
        return ResponseEntity.ok(orderService.update(id, updated));
    }

    @DeleteMapping("/{id}") //MUST-Deletes an order by its ID.
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
