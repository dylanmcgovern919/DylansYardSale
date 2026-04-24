package com.dylansyardsale.dto;

// Response DTO for Tag — avoids exposing entity directly and prevents lazy serialization issues.
public class TagResponse {
    private Long id;
    private String name;

    public TagResponse() {}

    public TagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}
