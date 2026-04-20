package com.acadflow.module4.entity;

import com.acadflow.module1.entity.Subject;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(length = 50)
    private String type; // "MIDTERM", "FINAL", "QUIZ", etc.

    @Column(length = 100)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    private Float maxMarks;
    private Integer duration; // in minutes

    // Constructors
    public Exam() {}

    public Exam(Subject subject, LocalDateTime date, String type, String location) {
        this.subject = subject;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
