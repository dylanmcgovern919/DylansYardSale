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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

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

    @Test
    void create_returnsValidationErrors_whenPayloadInvalid() throws Exception {
        String invalidPayload = """
                {
                  "name": "",
                  "description": "Invalid product used to demonstrate validation",
                  "price": -5.0,
                  "category": "",
                  "genre": ""
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.fields.name").value("name is required"))
                .andExpect(jsonPath("$.fields.price").value("price must be greater than 0"))
                .andExpect(jsonPath("$.fields.category").value("category is required"))
                .andExpect(jsonPath("$.fields.genre").value("genre is required"));
    }
}
