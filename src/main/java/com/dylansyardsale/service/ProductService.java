package com.dylansyardsale.service;

import com.dylansyardsale.dto.ProductResponse;
import com.dylansyardsale.dto.TagResponse;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Product;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.repository.ProductRepository;
import com.dylansyardsale.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service // Marks this class as a Spring-managed service bean containing business logic for Product operations.
public class ProductService {
    private final ProductRepository productRepository; //Use these to talk to the database and perform CRUD operations on products and tags.
    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    // Converts a Product entity to a ProductResponse DTO to avoid exposing JPA entities directly.
    private ProductResponse toResponse(Product product) {
        Set<TagResponse> tagResponses = product.getTags().stream()
            .map(t -> new TagResponse(t.getId(), t.getName()))
            .collect(Collectors.toSet());
        return new ProductResponse(
            product.getId(), product.getName(), product.getDescription(),
            product.getPrice(), product.getCategory(), product.getGenre(), tagResponses
        );
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getOne(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse create(Product product) {
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, Product updated) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setCategory(updated.getCategory());
        product.setGenre(updated.getGenre());
        return toResponse(productRepository.save(product)); //Saves the updated entity and returns with latest version
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductResponse addTag(Long id, String tagName) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        //Basic input validation for tagName
        if (tagName == null || tagName.trim().isEmpty()) {
            throw new IllegalArgumentException("tagName must not be blank");
        }

        Tag tag = tagRepository.findByNameIgnoreCase(tagName.trim());
        if (tag == null) tag = tagRepository.save(new Tag(tagName.trim()));

        //Set to stop duplicates, and this extra check just makes it more clear what I'm trying to do.
        product.addTag(tag); // use helper method for bidirectional consistency
        return toResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public Set<TagResponse> getProductTags(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return product.getTags().stream()
            .map(t -> new TagResponse(t.getId(), t.getName()))
            .collect(Collectors.toSet());
    }

    @Transactional
    public ProductResponse removeTag(Long id, Long tagId) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));

        product.removeTag(tag); // use helper method for bidirectional consistency
        return toResponse(productRepository.save(product));
    }
}
