package com.acadflow.module2.service;

import com.acadflow.module2.entity.Resource;
import com.acadflow.module2.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository repo;

    public ResourceService(ResourceRepository repo) {
        this.repo = repo;
    }

    public Resource add(Resource resource) {
        return repo.save(resource);
    }

    public List<Resource> getBySubject(Long subjectId) {
        return repo.findBySubjectId(subjectId);
    }
}