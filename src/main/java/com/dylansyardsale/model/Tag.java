package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags") 
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false) // Enforce uniqueness at DB level to prevent duplicate tag names
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Product> products = new HashSet<>(); // Keeps inverse side of many-to-many with Product; no schema name change needed here.

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }
}