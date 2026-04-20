package com.acadflow.module2.controller;

import com.acadflow.module2.entity.Resource;
import com.acadflow.module2.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService service;

    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @PostMapping
    public Resource add(@RequestBody Resource resource) {
        return service.add(resource);
    }

    @GetMapping("/{subjectId}")
    public List<Resource> getBySubject(@PathVariable Long subjectId) {
        return service.getBySubject(subjectId);
    }
}