package com.dylansyardsale.controller;

import com.dylansyardsale.dto.ProductResponse;
import com.dylansyardsale.dto.TagResponse;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController // Marks this class as a REST API controller, allowing it to handle HTTP requests and return JSON responses.
@RequestMapping("/api/products") // Every route in this controller starts with /api/products.
public class ProductController { 
    private final ProductService productService; //Delegates all business logic to the product service layer.

    public ProductController(ProductService productService) { //Spring automatically injects the service this controller depends on through the constructor.
        this.productService = productService;
    }

    @GetMapping // Gets all products from the database and returns them as a list. Each product includes its associated tags.
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}") // Looks up a single product by its ID. If missing, it throws a custom ResourceNotFoundException
    public ResponseEntity<ProductResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getOne(id));
    }

    @PostMapping // Creates a new product in the database.
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product)); //Saves the new product to the database and returns it.
    }

    @PutMapping("/{id}") // Updates an existing product by its ID. If the product doesn't exist, it throws a custom ResourceNotFoundException.
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody Product updated) {
        return ResponseEntity.ok(productService.update(id, updated)); //Saves the updated entity and returns with latest version
    }

    @DeleteMapping("/{id}") // Deletes a product by its ID. If the product doesn't exist, it throws a custom ResourceNotFoundException.
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags") // Adds a tag to a product. If the product doesn't exist, it throws a custom ResourceNotFoundException.
    public ResponseEntity<ProductResponse> addTag(@PathVariable Long id, @RequestParam String tagName) {
        return ResponseEntity.ok(productService.addTag(id, tagName));
    }

    @GetMapping("/{id}/tags") // Retrieves tags for one product.
    public ResponseEntity<Set<TagResponse>> getProductTags(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductTags(id));
    }

    @DeleteMapping("/{id}/tags/{tagId}") // Removes a tag from a product. If the product or tag doesn't exist, it throws a custom ResourceNotFoundException.
    public ResponseEntity<ProductResponse> removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        return ResponseEntity.ok(productService.removeTag(id, tagId));
    }
}
