package com.dylansyardsale.service;

import com.dylansyardsale.dto.TagResponse;
import com.dylansyardsale.exception.ResourceNotFoundException;
import com.dylansyardsale.model.Tag;
import com.dylansyardsale.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service //MUST-Marks this class as a Spring-managed service bean containing business logic for Tag operations.
public class TagService {
    private final TagRepository tagRepository; // Repository for database access

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Converts a Tag entity to a TagResponse DTO to avoid exposing JPA entities directly.
    private TagResponse toResponse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }

    @Transactional(readOnly = true)
    public List<TagResponse> getAll() {
        return tagRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TagResponse getOne(Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        return toResponse(tag);
    }

    @Transactional(readOnly = true)
    public TagResponse findByName(String name) {
        Tag tag = tagRepository.findByName(name);
        return tag != null ? toResponse(tag) : null;
    }

    @Transactional
    public TagResponse create(Tag tag) {
        return toResponse(tagRepository.save(tag));
    }

    @Transactional
    public TagResponse update(Long id, Tag updated) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tag.setName(updated.getName());
        return toResponse(tagRepository.save(tag));
    }

    @Transactional
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found: " + id);
        }
        tagRepository.deleteById(id);
    }
}
