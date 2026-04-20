package com.acadflow.module2.controller;

import com.acadflow.module2.dto.SubmissionRequestDTO;
import com.acadflow.module2.dto.SubmissionResponseDTO;
import com.acadflow.module2.entity.Submission;
import com.acadflow.module2.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // CREATE submission
    @PostMapping
    public ResponseEntity<Submission> create(@RequestBody SubmissionRequestDTO request) {
        return ResponseEntity.ok(
                submissionService.create(request.assignmentId, request.userId)
        );
    }

    // GET submissions by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(submissionService.getByUser(userId));
    }
}