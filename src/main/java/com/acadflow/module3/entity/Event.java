package com.acadflow.module3.entity;

import com.acadflow.module1.entity.Subject;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String description;
    private Boolean isImportant = false;

    // Constructors
    public Event() {}

    public Event(String title, EventType type, LocalDateTime date, Subject subject) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.subject = subject;
    }

    public Event(String title, EventType type, LocalDateTime date, Subject subject, String description, Boolean isImportant) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.subject = subject;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
