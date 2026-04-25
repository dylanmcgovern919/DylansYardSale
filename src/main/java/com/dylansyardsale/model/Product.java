package com.dylansyardsale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Entity //REQUIRED
@Table(name = "products") //REQUIRED
public class Product {

    @Id //REQUIRED
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED
    private Long id; //REQUIRED - DB-generated ID for each item 

    @NotBlank(message = "name is required")
    private String name; //REQUIRED (domain field)

    @NotBlank(message = "description is required")
    private String description; //REQUIRED (domain field)

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private Double price; //REQUIRED (domain field)

    @NotNull(message = "category is required")
    @Enumerated(EnumType.STRING) //REQUIRED
    private ProductCategory category; //REQUIRED

    //Kept because your existing constructor/DataLoader uses genre text.
    @NotBlank(message = "genre is required")
    private String genre;

    // REQUIRED: many-to-many relationship (Product <-> Tag)
    @ManyToMany //REQUIRED
    @JoinTable( //REQUIRED
        name = "product_tags", //REQUIRED (join table)
        joinColumns = @JoinColumn(name = "product_id"), //REQUIRED
        inverseJoinColumns = @JoinColumn(name = "tag_id") //REQUIRED
    )
    private Set<Tag> tags = new HashSet<>(); //REQUIRED

    public Product() {} //REQUIRED by JPA

    // Matches your existing DataLoader constructor calls.
    public Product(String name, String description, Double price, ProductCategory category, String genre) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.genre = genre;
    }

    // REQUIRED: id getter so API can return item ID
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    // REQUIRED for CRUD on many-to-many relationship
    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }

    // Helper methods to keep both sides of the many-to-many relationship in sync in memory.
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getProducts().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getProducts().remove(this);
    }

}
