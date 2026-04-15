package com.acadflow.module4.controller;

import com.acadflow.module4.dto.ExamCreateDTO;
import com.acadflow.module4.dto.ExamResponseDTO;
import com.acadflow.module4.service.ExamService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Exam Management
 * Endpoints:
 * - POST /api/v1/exams - Create exam
 * - GET /api/v1/exams - Get all exams (ordered by date)
 * - GET /api/v1/exams/{id} - Get specific exam
 * - GET /api/v1/exams/subject/{subjectId} - Get exams for subject
 * - GET /api/v1/exams/details/{subjectId} - Get exam details for subject
 * - GET /api/v1/exams/upcoming/all - Get all upcoming exams
 * - GET /api/v1/exams/date-range - Get exams in date range
 * - PUT /api/v1/exams/{id} - Update exam
 * - DELETE /api/v1/exams/{id} - Delete exam
 */
@RestController
@RequestMapping("/api/v1/exams")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * Create a new exam
     * @param dto the exam creation DTO
     * @return the created exam response
     */
    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody ExamCreateDTO dto) {
        try {
            ExamResponseDTO response = examService.createExam(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all exams ordered by date
     */
    @GetMapping
    public ResponseEntity<List<ExamResponseDTO>> getAllExams() {
        List<ExamResponseDTO> exams = examService.getAllExamsOrdered();
        return ResponseEntity.ok(exams);
    }

    /**
     * Get exam by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getExam(@PathVariable Long id) {
        try {
            ExamResponseDTO exam = examService.getExam(id);
            return ResponseEntity.ok(exam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all exams for a specific subject
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getExamsForSubject(@PathVariable Long subjectId) {
        try {
            List<ExamResponseDTO> exams = examService.getExamsForSubject(subjectId);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get exam details for a subject (with full information)
     */
    @GetMapping("/details/{subjectId}")
    public ResponseEntity<?> getExamDetails(@PathVariable Long subjectId) {
        try {
            List<ExamResponseDTO> exams = examService.getExamDetails(subjectId);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all upcoming exams after today
     */
    @GetMapping("/upcoming/all")
    public ResponseEntity<List<ExamResponseDTO>> getAllUpcomingExams() {
        List<ExamResponseDTO> exams = examService.getAllUpcomingExams();
        return ResponseEntity.ok(exams);
    }

    /**
     * Get exams within a date range
     * Query parameters: startDate and endDate (format: yyyy-MM-ddTHH:mm:ss)
     */
    @GetMapping("/date-range")
    public ResponseEntity<?> getExamsInDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<ExamResponseDTO> exams = examService.getUpcomingExams(startDate, endDate);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Update an exam
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(
            @PathVariable Long id,
            @RequestBody ExamCreateDTO dto) {
        try {
            ExamResponseDTO updated = examService.updateExam(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete an exam
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }
}
