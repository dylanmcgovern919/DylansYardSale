package com.dylansyardsale.service.impl;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.OrderItem;
import com.dylansyardsale.repository.OrderItemRepository;
import com.dylansyardsale.repository.OrderRepository;
import com.dylansyardsale.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void delete_removesItem_whenOrderAndItemExist() {
        OrderItem orderItem = new OrderItem();
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderItemRepository.findByIdAndOrderId(2L, 1L)).thenReturn(Optional.of(orderItem));

        orderItemService.delete(1L, 2L);

        verify(orderItemRepository).delete(orderItem);
    }

    @Test
    void delete_throws_whenOrderMissing() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderItemService.delete(1L, 2L));
        verify(orderItemRepository, never()).findByIdAndOrderId(2L, 1L);
        verify(orderItemRepository, never()).delete(org.mockito.ArgumentMatchers.any(OrderItem.class));
    }

    @Test
    void delete_throws_whenItemNotInOrder() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderItemRepository.findByIdAndOrderId(99L, 1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderItemService.delete(1L, 99L));
        verify(orderItemRepository, never()).delete(org.mockito.ArgumentMatchers.any(OrderItem.class));
    }
}
