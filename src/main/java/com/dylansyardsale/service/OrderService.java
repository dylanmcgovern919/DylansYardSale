package com.dylansyardsale.service;

import com.dylansyardsale.dto.OrderItemResponse;
import com.dylansyardsale.dto.OrderRequest;
import com.dylansyardsale.dto.OrderResponse;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Order;
import com.dylansyardsale.model.OrderItem;
import com.dylansyardsale.model.OrderStatus;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.repository.OrderRepository;
import com.dylansyardsale.repository.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Service
@Validated
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService (OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getProduct() != null ? item.getProduct().getName() : null,
                item.getQuantity()
            ))
            .toList();

        BigDecimal packaging = null;
        if (order.getPackagingCost() != null) {
            packaging = BigDecimal.valueOf(order.getPackagingCost());
        }

        return new OrderResponse(
            order.getId(),
            order.getOrderDate(),
            order.getStatus(),
            packaging,
            itemResponses
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
        return orderRepository.findAll().stream()
            .filter(o -> o.getStatus() == status)
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setPackagingCost(request.getPackagingCost());

        List<OrderItem> items = new ArrayList<>();
        for (var itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemRequest.getQuantity());
            items.add(item);
        }

        order.setItems(items);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse update(Long id, @Valid OrderRequest updatedRequest) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        order.setStatus(updatedRequest.getStatus());
        order.setPackagingCost(updatedRequest.getPackagingCost());

        if (updatedRequest.getItems() != null && !updatedRequest.getItems().isEmpty()) {
            List<OrderItem> items = new ArrayList<>();

            for (var itemRequest : updatedRequest.getItems()) {
                Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));

                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setOrder(order);
                item.setQuantity(itemRequest.getQuantity());
                items.add(item);
            }

            order.getItems().clear();
            order.getItems().addAll(items);
        }

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