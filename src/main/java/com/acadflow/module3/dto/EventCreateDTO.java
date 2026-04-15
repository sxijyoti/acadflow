package com.acadflow.module3.dto;

import com.acadflow.module3.entity.EventType;
import java.time.LocalDateTime;

/**
 * DTO for creating/updating events
 * Used in API requests to create or modify event data
 */
public class EventCreateDTO {
    private String title;
    private EventType type;
    private LocalDateTime date;
    private Long subjectId;
    private String description;
    private Boolean isImportant;

    // Constructors
    public EventCreateDTO() {}

    public EventCreateDTO(String title, EventType type, LocalDateTime date, Long subjectId, 
                         String description, Boolean isImportant) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.subjectId = subjectId;
        this.description = description;
        this.isImportant = isImportant;
    }

    // Getters & Setters
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
