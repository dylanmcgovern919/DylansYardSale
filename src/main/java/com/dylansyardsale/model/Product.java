
package com.dylansyardsale.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB-generated ID for each item

    private String name; // (domain field)
    private String description; // (domain field)
    private Double price; // (domain field)

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    //Kept because your existing constructor/DataLoader uses genre text.
    private String genre;

    // : many-to-many relationship (Product <-> Tag)
    @ManyToMany
    @JoinTable(
        name = "product_tags", // (join table)
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Product() {} // by JPA

    // Matches your existing DataLoader constructor calls.
    public Product(String name, String description, Double price, ProductCategory category, String genre) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.genre = genre;
    }

    // : id getter so API can return item ID
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

    // for CRUD on many-to-many relationship
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
