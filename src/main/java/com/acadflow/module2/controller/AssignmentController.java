package com.acadflow.module2.controller;

import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.service.AssignmentService;
import com.acadflow.module2.dto.AssignmentCreateDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @PostMapping
    public Assignment create(@RequestBody AssignmentCreateDTO dto) {

        Assignment a = new Assignment();
        a.setTitle(dto.title);
        a.setDescription(dto.description);
        a.setDeadline(dto.deadline);

        return service.create(a, dto.subjectId);
    }

    @GetMapping("/student/{userId}")
    public List<Assignment> getForStudent(@PathVariable Long userId) {
        return service.getAssignmentsForStudent(userId);
    }
}