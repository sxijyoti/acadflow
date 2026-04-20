package com.acadflow.module4.event;

/**
 * Observer interface for listening to exam events
 * Implements Observer Pattern - Dependency Inversion Principle
 */
public interface ExamEventListener {
    void onExamCreated(ExamEvent event);
    void onExamUpdated(ExamEvent event);
    void onExamDeleted(ExamEvent event);
}
