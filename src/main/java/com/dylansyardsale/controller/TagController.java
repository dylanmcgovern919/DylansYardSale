package com.dylansyardsale.controller;

import com.dylansyardsale.dto.TagResponse;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Tells Spring this class handles REST API requests and returns JSON responses.
@RequestMapping("/api/tags") // Base URL so every endpoint here starts with /api/tags.
public class TagController {
    private final TagService tagService; //Delegates all business logic to the tag service layer.

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping // Gets all tags from the database and returns them as a list.
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}") // Gets one tag by ID.
    public ResponseEntity<TagResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getOne(id));
    }

    @PostMapping // Creates a new tag.
    public ResponseEntity<TagResponse> create(@Valid @RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
    }

    @PutMapping("/{id}") // Updates an existing tag.
    public ResponseEntity<TagResponse> update(@PathVariable Long id, @Valid @RequestBody Tag updated) {
        return ResponseEntity.ok(tagService.update(id, updated));
    }

    @DeleteMapping("/{id}") // Deletes a tag by ID. 
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}