package com.dylansyardsale.controller;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.repository.ProductRepository;
import com.dylansyardsale.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //REQUIRED
@RequestMapping("/api/products") //REQUIRED
public class ProductController {
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public ProductController(ProductRepository productRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping //REQUIRED
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}") //REQUIRED
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return ResponseEntity.ok(product);
    }

    @PostMapping //REQUIRED
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }

    @PutMapping("/{id}") //REQUIRED
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product updated) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setCategory(updated.getCategory());
        product.setSubcategory(updated.getSubcategory());
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{id}") //REQUIRED
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags") //REQUIRED
    public ResponseEntity<Product> addTag(@PathVariable Long id, @RequestParam String tagName) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        Tag tag = tagRepository.findByName(tagName);
        if (tag == null) tag = tagRepository.save(new Tag(tagName));

        product.getTags().add(tag);
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{id}/tags/{tagId}") //REQUIRED
    public ResponseEntity<Product> removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));

        product.getTags().remove(tag);
        return ResponseEntity.ok(productRepository.save(product));
    }
}
