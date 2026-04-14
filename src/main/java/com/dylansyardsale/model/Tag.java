package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import java.util.HashSet;

// Tag is a required many-to-many entity //REQUIRED
@Entity //REQUIRED
public class Tag {
    @Id //REQUIRED
    @GeneratedValue(strategy = GenerationType.IDENTITY) //REQUIRED
    private Long id; //REQUIRED

    @NotBlank
    private String name; //REQUIRED

    @JsonIgnore
    @ManyToMany(mappedBy = "tags") //REQUIRED
    private Set<Product> products = new HashSet<>();

    public Tag() {} //REQUIRED

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }
}

