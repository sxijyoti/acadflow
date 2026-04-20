package com.acadflow.module4.dto;

import java.time.LocalDateTime;

/**
 * DTO for responding with exam information
 */
public class ExamResponseDTO {

    private Long id;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private LocalDateTime date;
    private String type;
    private String location;
    private String instructions;
    private Float maxMarks;
    private Integer duration;

    // Constructors
    public ExamResponseDTO() {}

    public ExamResponseDTO(Long id, Long subjectId, String subjectName, LocalDateTime date, 
                          String type, String location) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.date = date;
        this.type = type;
        this.location = location;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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
