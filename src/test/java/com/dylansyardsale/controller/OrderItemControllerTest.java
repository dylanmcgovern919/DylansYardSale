package com.dylansyardsale.controller;

import com.dylansyardsale.exception.GlobalExceptionHandler;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
@Import(GlobalExceptionHandler.class)
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Test
    void delete_returnsNoContent_whenItemExists() throws Exception {
        mockMvc.perform(delete("/api/orders/1/items/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(orderItemService).delete(1L, 2L);
    }

    @Test
    void delete_returnsNotFound_whenItemMissing() throws Exception {
        doThrow(new ResourceNotFoundException("OrderItem not found"))
                .when(orderItemService).delete(1L, 99L);

        mockMvc.perform(delete("/api/orders/1/items/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("OrderItem not found"));
    }
}
