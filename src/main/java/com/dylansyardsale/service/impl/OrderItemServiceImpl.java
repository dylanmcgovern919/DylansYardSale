package com.dylansyardsale.service.impl;

import com.dylansyardsale.dto.OrderItemRequest;
import com.dylansyardsale.dto.OrderItemResponse;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Order;
import com.dylansyardsale.model.OrderItem;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.repository.OrderItemRepository;
import com.dylansyardsale.repository.OrderRepository;
import com.dylansyardsale.repository.ProductRepository;
import com.dylansyardsale.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

//MUST-Provides business logic for OrderItem CRUD operations scoped to a parent Order.
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderItemServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    //MUST-Returns all items for a given order.
    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getAllByOrder(Long orderId) {
        ensureOrderExists(orderId);
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //MUST-Returns one item for a given order.
    @Override
    @Transactional(readOnly = true)
    public OrderItemResponse getOne(Long orderId, Long itemId) {
        ensureOrderExists(orderId);
        OrderItem item = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for order id " + orderId + " and item id " + itemId));
        return toResponse(item);
    }

    //MUST-Creates one item under a given order.
    @Override
    @Transactional
    public OrderItemResponse create(Long orderId, OrderItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + request.getProductId()));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        OrderItem saved = orderItemRepository.save(item);
        return toResponse(saved);
    }

    //MUST-Updates one item under a given order.
    @Override
    @Transactional
    public OrderItemResponse update(Long orderId, Long itemId, OrderItemRequest request) {
        ensureOrderExists(orderId);

        OrderItem item = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for order id " + orderId + " and item id " + itemId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + request.getProductId()));

        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        OrderItem saved = orderItemRepository.save(item);
        return toResponse(saved);
    }

    //MUST-Deletes one item under a given order.
    @Override
    @Transactional
    public void delete(Long orderId, Long itemId) {
        ensureOrderExists(orderId);

        OrderItem item = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for order id " + orderId + " and item id " + itemId));

        orderItemRepository.delete(item);
    }

    //MUST-Maps OrderItem entity to OrderItemResponse DTO.
    private OrderItemResponse toResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity()
        );
    }

    //MUST-Validates parent order existence for nested endpoints.
    private void ensureOrderExists(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }
    }
}