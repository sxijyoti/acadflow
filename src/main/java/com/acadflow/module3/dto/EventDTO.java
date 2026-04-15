package com.acadflow.module3.dto;

import com.acadflow.module3.entity.EventType;
import java.time.LocalDateTime;

/**
 * DTO for Event responses
 * Used in API responses to expose event data
 */
public class EventDTO {
    private Long id;
    private String title;
    private EventType type;
    private LocalDateTime date;
    private Long subjectId;
    private String subjectName;
    private String description;
    private Boolean isImportant;

    // Constructors
    public EventDTO() {}

    public EventDTO(Long id, String title, EventType type, LocalDateTime date, Long subjectId, 
                   String subjectName, String description, Boolean isImportant) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = date;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
        this.isImportant = isImportant;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }
}
