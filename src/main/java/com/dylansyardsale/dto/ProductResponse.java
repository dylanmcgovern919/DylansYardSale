package com.dylansyardsale.dto;

import com.dylansyardsale.model.ProductCategory;
import java.util.Set;

// Response DTO for Product — avoids exposing entity directly and prevents lazy serialization issues.
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private ProductCategory category;
    private String genre;
    private Set<TagResponse> tags;

    public ProductResponse() {}

    public ProductResponse(Long id, String name, String description, Double price,
                           ProductCategory category, String genre, Set<TagResponse> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.genre = genre;
        this.tags = tags;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public ProductCategory getCategory() { return category; }
    public String getGenre() { return genre; }
    public Set<TagResponse> getTags() { return tags; }
}
