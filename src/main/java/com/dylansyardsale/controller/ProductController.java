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
import java.util.Set; // ADDED NOTE: Needed for tags endpoint

@RestController //MUST- Marks this class as a REST API controller, allowing it to handle HTTP requests and return JSON responses.
@RequestMapping("/api/products") //MUST-Every route in this controller starts with /api/products.
public class ProductController { 
    private final ProductRepository productRepository;//Use these to talk to the database and perform CRUD operations on products and tags.
    private final TagRepository tagRepository;

    public ProductController(ProductRepository productRepository, TagRepository tagRepository) {//Spring automatically injects the repositories this controller depends on through the constructor.
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping //MUST-Gets all products from the database and returns them as a list. Each product includes its associated tags.
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}") //MUST-Looks up a single product by its ID. If missing, it throws a custom ResourceNotFoundException 
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return ResponseEntity.ok(product);
    }

    @PostMapping //MUST-Creates a new product in the database.
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));//Saves the new product to the database and returns it.
    }

    @PutMapping("/{id}") //MUST-Updates an existing product by its ID. If the product doesn't exist, it throws a custom ResourceNotFoundException.  
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product updated) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setCategory(updated.getCategory());
        product.setGenre(updated.getGenre());
        return ResponseEntity.ok(productRepository.save(product));//Saves the updated entity and returns with latest version
    }
    @DeleteMapping("/{id}") //MUST-Deletes a product by its ID. If the product doesn't exist, it throws a custom ResourceNotFoundException. 
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags") //MUST-Adds a tag to a product. If the product doesn't exist, it throws a custom ResourceNotFoundException. 
    public ResponseEntity<Product> addTag(@PathVariable Long id, @RequestParam String tagName) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        // ADDED NOTE: Basic input validation for tagName
        if (tagName == null || tagName.trim().isEmpty()) {
            throw new IllegalArgumentException("tagName must not be blank");
        }

        Tag tag = tagRepository.findByName(tagName.trim());
        if (tag == null) tag = tagRepository.save(new Tag(tagName.trim()));

        // ADDED NOTE: Set prevents duplicates, but this check improves clarity of intent
        if (!product.getTags().contains(tag)) {
            product.getTags().add(tag);
        }

        return ResponseEntity.ok(productRepository.save(product));
    }

    @GetMapping("/{id}/tags") // ADDED NOTE: Added endpoint to retrieve tags for one product
    public ResponseEntity<Set<Tag>> getProductTags(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return ResponseEntity.ok(product.getTags());
    }

    @DeleteMapping("/{id}/tags/{tagId}") //MUST-Removes a tag from a product. If the product or tag doesn't exist, it throws a custom ResourceNotFoundException.
    public ResponseEntity<Product> removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));

        product.getTags().remove(tag);
        return ResponseEntity.ok(productRepository.save(product));
    }
}