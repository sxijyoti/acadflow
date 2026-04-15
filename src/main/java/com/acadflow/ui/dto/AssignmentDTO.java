package com.acadflow.ui.dto;

import java.time.LocalDate;

/**
 * Assignment DTO
 */
public class AssignmentDTO {
    private Long id;
    private String title;
    private String description;
    private String subjectCode;
    private String subjectName;
    private LocalDate dueDate;
    private String status; // PENDING, SUBMITTED, LATE, GRADED
    private String filePath;
    private Double score;
    private String feedback;

    public AssignmentDTO() {}

    public AssignmentDTO(Long id, String title, String subjectCode, LocalDate dueDate, String status) {
        this.id = id;
        this.title = title;
        this.subjectCode = subjectCode;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
