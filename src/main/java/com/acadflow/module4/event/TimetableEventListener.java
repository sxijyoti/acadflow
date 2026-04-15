package com.acadflow.module4.event;

/**
 * Observer interface for listening to timetable events
 * Implements Observer Pattern - Dependency Inversion Principle
 */
public interface TimetableEventListener {
    void onTimetableCreated(TimetableEvent event);
    void onTimetableUpdated(TimetableEvent event);
    void onTimetableDeleted(TimetableEvent event);
}
