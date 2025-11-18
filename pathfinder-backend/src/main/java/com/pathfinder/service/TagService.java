package com.pathfinder.service;

import com.pathfinder.exception.TagNotFoundException;
import com.pathfinder.model.Tag;
import com.pathfinder.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public Tag getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    public List<Tag> getByIds(List<Long> ids) {
        return ids.stream()
                .map(this::getById)
                .toList();
    }
}
