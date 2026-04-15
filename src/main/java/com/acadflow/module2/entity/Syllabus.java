package com.acadflow.module2.entity;

import com.acadflow.module1.entity.Subject;
import jakarta.persistence.*;

@Entity
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private Subject subject;

    // getters & setters

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}