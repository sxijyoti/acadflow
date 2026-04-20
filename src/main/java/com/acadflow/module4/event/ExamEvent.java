package com.acadflow.module4.event;

import com.acadflow.module4.entity.Exam;

/**
 * Event published when an exam is created or modified
 */
public class ExamEvent {

    public enum ExamEventType {
        CREATED,
        UPDATED,
        DELETED
    }

    private ExamEventType eventType;
    private Exam exam;
    private Long timestamp;

    // Constructors
    public ExamEvent(ExamEventType eventType, Exam exam) {
        this.eventType = eventType;
        this.exam = exam;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters & Setters
    public ExamEventType getEventType() {
        return eventType;
    }

    public void setEventType(ExamEventType eventType) {
        this.eventType = eventType;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
