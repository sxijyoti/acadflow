package com.acadflow.ui.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Exam DTO
 */
public class ExamDTO {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type; // MIDTERM, FINAL, QUIZ, PRACTICAL
    private String location;
    private int totalMarks;
    private Double obtainedMarks;

    public ExamDTO() {}

    public ExamDTO(Long id, String subjectCode, String subjectName, LocalDate examDate, String type, String location) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.type = type;
        this.location = location;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public Double getObtainedMarks() { return obtainedMarks; }
    public void setObtainedMarks(Double obtainedMarks) { this.obtainedMarks = obtainedMarks; }
}
