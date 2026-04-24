package com.dylansyardsale.controller;

import com.dylansyardsale.dto.TagResponse;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //MUST-Tells Spring this class handles REST API requests and returns JSON responses.
@RequestMapping("/api/tags") //MUST-Base URL so every endpoint here starts with /api/tags.
public class TagController {
    private final TagService tagService; //Delegates all business logic to the tag service layer.

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping //MUST-Gets all tags from the database and returns them as a list.
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}") //MUST-Gets one tag by ID.
    public ResponseEntity<TagResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getOne(id));
    }

    @PostMapping //MUST-Creates a new tag.
    public ResponseEntity<TagResponse> create(@Valid @RequestBody Tag tag) {
        // Prevent duplicate tags by name
        TagResponse existing = tagService.findByName(tag.getName());
        if (existing != null) {
            return ResponseEntity.ok(existing); // Return existing tag if duplicate name is requested
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
    }

    @PutMapping("/{id}") //MUST-Updates an existing tag.
    public ResponseEntity<TagResponse> update(@PathVariable Long id, @Valid @RequestBody Tag updated) {
        return ResponseEntity.ok(tagService.update(id, updated));
    }

    @DeleteMapping("/{id}") //MUST-Deletes a tag by ID. 
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
