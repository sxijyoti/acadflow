package com.acadflow.module2.controller;

import com.acadflow.module2.entity.Syllabus;
import com.acadflow.module2.service.SyllabusService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusController {

    private final SyllabusService service;

    public SyllabusController(SyllabusService service) {
        this.service = service;
    }

    // CREATE / UPDATE
    @PostMapping
    public Syllabus create(@RequestBody Map<String, Object> body) {

        Long subjectId = Long.valueOf(body.get("subjectId").toString());
        String content = body.get("content").toString();

        return service.create(subjectId, content);
    }

    // GET
    @GetMapping("/{subjectId}")
    public Syllabus get(@PathVariable Long subjectId) {
        return service.get(subjectId);
    }
}