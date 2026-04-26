package com.dylansyardsale.repository;

import com.dylansyardsale.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

//for CRUD operations on Tag entity
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
    Tag findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}