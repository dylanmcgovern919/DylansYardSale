package com.dylansyardsale.controller;

import com.dylansyardsale.dto.OrderItemRequest;
import com.dylansyardsale.dto.OrderItemResponse;
import com.dylansyardsale.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Marks this class as a REST API controller for nested OrderItem endpoints under orders.
@RestController
//Defines the base URL for order-item endpoints.
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemController {

    //Delegates all business logic to the order item service layer.
    private final OrderItemService orderItemService;

    //Gives Spring the service this controller depends on.
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    //Gets all items for a specific order.
    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getAllByOrder(orderId));
    }

    //Gets one item by orderId + itemId.
    @GetMapping("/{itemId}")
    public ResponseEntity<OrderItemResponse> getOne(@PathVariable Long orderId, @PathVariable Long itemId) {
        return ResponseEntity.ok(orderItemService.getOne(orderId, itemId));
    }

    //Creates a new item in a specific order.
    @PostMapping
    public ResponseEntity<OrderItemResponse> create(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderItemService.create(orderId, request));
    }

    //Updates quantity/product for one item in a specific order.
    @PutMapping("/{itemId}")
    public ResponseEntity<OrderItemResponse> update(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @Valid @RequestBody OrderItemRequest request
    ) {
        return ResponseEntity.ok(orderItemService.update(orderId, itemId, request));
    }

    //Deletes one item from a specific order.
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long orderId, @PathVariable Long itemId) {
        orderItemService.delete(orderId, itemId);
        return ResponseEntity.noContent().build();
    }

}
