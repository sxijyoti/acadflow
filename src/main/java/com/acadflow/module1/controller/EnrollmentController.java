package com.acadflow.module1.controller;

import com.acadflow.module1.dto.EnrollmentRequestDTO;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enroll(@RequestBody EnrollmentRequestDTO request) {
        return ResponseEntity.ok(enrollmentService.enroll(request.userId, request.subjectId));
    }

    @PostMapping("/drop")
    public ResponseEntity<Enrollment> drop(@RequestBody EnrollmentRequestDTO request) {
        return ResponseEntity.ok(enrollmentService.drop(request.userId, request.subjectId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getEnrolledSubjects(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getEnrolledSubjects(userId));
    }
}
