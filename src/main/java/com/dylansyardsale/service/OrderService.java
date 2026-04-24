package com.dylansyardsale.service;

import com.dylansyardsale.dto.OrderItemRequest;
import com.dylansyardsale.dto.OrderItemResponse;
import com.dylansyardsale.dto.OrderRequest;
import com.dylansyardsale.dto.OrderResponse;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.*;
import com.dylansyardsale.repository.OrderRepository;
import com.dylansyardsale.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service //MUST-Marks this class as a Spring-managed service bean containing business logic for Order operations.
public class OrderService {
    private final OrderRepository orderRepository;

    //Added ProductRepository to look up products when building order items.
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // Converts an Order entity to an OrderResponse DTO to avoid exposing JPA entities and prevent lazy serialization issues.
    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity()
            ))
            .toList();
        return new OrderResponse(
            order.getId(), order.getOrderDate(), order.getStatus(),
            order.getPackagingCost(), itemResponses
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOne(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getByStatus(OrderStatus status) {
        // Added a new filter endpoint so users only get items that match what they choose, instead of getting everything.
        return orderRepository.findAll().stream()
            .filter(o -> o.getStatus() == status)
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        // Explicit guard to prevent creating empty orders
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        Order order = new Order(); //Set the order date to the current date and time when the order is created, and set the status and packaging cost based on the request body.
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        //Logic to map Product IDs to specific OrderItems in the database
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));

            OrderItem item = new OrderItem(); //Connect item to the selected product and back to the parent order
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemRequest.getQuantity());
            items.add(item);
        }

        order.setItems(items);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse update(Long id, Order updated) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(updated.getStatus());
        order.setPackagingCost(updated.getPackagingCost());
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found: " + id);
        }
        orderRepository.deleteById(id);
    }
}
