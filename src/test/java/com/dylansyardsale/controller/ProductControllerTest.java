package com.dylansyardsale.controller;

import com.dylansyardsale.dto.ProductResponse;
import com.dylansyardsale.exception.GlobalExceptionHandler;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.ProductCategory;
import com.dylansyardsale.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getOne_returnsProduct_whenFound() throws Exception {
        ProductResponse response = new ProductResponse(1L, "Jacket", "Vintage", 29.99, ProductCategory.CLOTHING, null, Set.of());
        when(productService.getOne(1L)).thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jacket"));
    }

    @Test
    void getOne_returnsNotFound_whenMissing() throws Exception {
        when(productService.getOne(999L)).thenThrow(new ResourceNotFoundException("Product not found: 999"));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Product not found: 999"));
    }
}
