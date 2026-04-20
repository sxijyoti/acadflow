package com.acadflow.module4.dto;

import java.time.LocalDateTime;

/**
 * DTO for creating a new exam
 */
public class ExamCreateDTO {

    private Long subjectId;
    private LocalDateTime date;
    private String type; // "MIDTERM", "FINAL", "QUIZ", etc.
    private String location;
    private String instructions;
    private Float maxMarks;
    private Integer duration; // in minutes

    // Constructors
    public ExamCreateDTO() {}

    public ExamCreateDTO(Long subjectId, LocalDateTime date, String type, String location) {
        this.subjectId = subjectId;
        this.date = date;
        this.type = type;
        this.location = location;
    }

    // Getters & Setters
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Float getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Float maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
