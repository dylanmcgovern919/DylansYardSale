package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags") //notes: Added explicit table name so Hibernate maps this entity to "tags" (not inferred "tag"), preventing FK mismatch errors.
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Product> products = new HashSet<>(); //notes: Keeps inverse side of many-to-many with Product; no schema name change needed here.

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