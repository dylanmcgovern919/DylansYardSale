package com.dylansyardsale.controller;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //MUST-Tells Spring this class handles REST API requests and returns JSON responses.
@RequestMapping("/api/tags") //MUST-Base URL so every endpoint here starts with /api/tags.
public class TagController {
    private final TagRepository tagRepository;
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping //MUST-Gets all tags from the database and returns them as a list.
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @GetMapping("/{id}") //MUST-Gets one tag by ID.
    public ResponseEntity<Tag> getOne(@PathVariable Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        return ResponseEntity.ok(tag);
    }

    @PostMapping //MUST-Creates a new tag.
    public ResponseEntity<Tag> create(@Valid @RequestBody Tag tag) {
        // ADDED NOTE: Prevent duplicate tags by name
        Tag existing = tagRepository.findByName(tag.getName());
        if (existing != null) {
            return ResponseEntity.ok(existing); // ADDED NOTE: Return existing tag if duplicate name is requested
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagRepository.save(tag));
    }

    @PutMapping("/{id}") //MUST-Updates an existing tag.
    public ResponseEntity<Tag> update(@PathVariable Long id, @Valid @RequestBody Tag updated) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tag.setName(updated.getName());
        return ResponseEntity.ok(tagRepository.save(tag));
    }

    @DeleteMapping("/{id}") //MUST-Deletes a tag by ID. 
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found: " + id);
        }
        tagRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}