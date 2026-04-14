package com.dylansyardsale.controller;

import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.repository.TagRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //REQUIRED
@RequestMapping("/api/tags") //REQUIRED
public class TagController {
    private final TagRepository tagRepository;
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping //REQUIRED
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @GetMapping("/{id}") //REQUIRED
    public ResponseEntity<Tag> getOne(@PathVariable Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        return ResponseEntity.ok(tag);
    }

    @PostMapping //REQUIRED
    public ResponseEntity<Tag> create(@Valid @RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagRepository.save(tag));
    }

    @PutMapping("/{id}") //REQUIRED
    public ResponseEntity<Tag> update(@PathVariable Long id, @Valid @RequestBody Tag updated) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tag.setName(updated.getName());
        return ResponseEntity.ok(tagRepository.save(tag));
    }

    @DeleteMapping("/{id}") //REQUIRED
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found: " + id);
        }
        tagRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
