package com.dylansyardsale.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

<<<<<<< HEAD
// COPILOT NOTE: Entity kept as core model for CRUD + relationships requirements.
@Entity //REQUIRED
@Table(name = "products") //REQUIRED
public class Product {

    @Id //REQUIRED
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED
    private Long id; //REQUIRED - DB-generated ID for each item (replaces QR usage)

    private String name; //REQUIRED (domain field)
    private String description; //REQUIRED (domain field)
    private Double price; //REQUIRED (domain field)

    @Enumerated(EnumType.STRING) //REQUIRED
    private ProductCategory category; //REQUIRED

    // COPILOT NOTE: Kept because your existing constructor/DataLoader uses genre text.
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

    // COPILOT NOTE: Matches your existing DataLoader constructor calls.
=======
@Entity // MUST - Marks Product as a JPA entity so Hibernate maps it to a database table.
@Table(name = "products") // MUST - Explicit table name for clear schema mapping and predictable SQL table naming.
public class Product {

    @Id  // MUST - Primary key annotation so each Product has a unique identifier.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MUST - Database auto-generates id values using identity/auto-increment strategy.
    private Long id; 
    private String name; 
    private String description; 
    private Double price; 

    @Enumerated(EnumType.STRING) // MUST - Stores enum as readable text in DB (safer/more readable than ordinal index).
    private ProductCategory category; 
 

    private String genre;

    @ManyToMany//MUST- many-to-many relationship (Product <-> Tag)
    @JoinTable( // MUST - Defines many-to-many relationship: one product can have many tags, one tag can belong to many products.
        name = "product_tags",  // MUST - Explicit join table name that stores Product↔Tag associations.
        joinColumns = @JoinColumn(name = "product_id"), // MUST - Join column pointing to this entity's primary key in join table.
        inverseJoinColumns = @JoinColumn(name = "tag_id")  // MUST - Inverse join column pointing to Tag primary key in join table.
    )
    private Set<Tag> tags = new HashSet<>(); 
 

    public Product() {} 

>>>>>>> 4ed9b39 (update from spring tools)
    public Product(String name, String description, Double price, ProductCategory category, String genre) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.genre = genre;
    }

<<<<<<< HEAD
    // REQUIRED: id getter so API can return item ID
=======
    // id getter so API can return item ID
>>>>>>> 4ed9b39 (update from spring tools)
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

<<<<<<< HEAD
    // COPILOT NOTE: qrCode field/getter/setter intentionally removed per your request.
=======
  
>>>>>>> 4ed9b39 (update from spring tools)
}
