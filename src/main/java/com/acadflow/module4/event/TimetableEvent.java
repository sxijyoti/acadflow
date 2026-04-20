package com.acadflow.module4.event;

import com.acadflow.module4.entity.Timetable;

/**
 * Event published when a timetable slot is created or modified
 */
public class TimetableEvent {

    public enum TimetableEventType {
        CREATED,
        UPDATED,
        DELETED
    }

    private TimetableEventType eventType;
    private Timetable timetable;
    private Long timestamp;

    // Constructors
    public TimetableEvent(TimetableEventType eventType, Timetable timetable) {
        this.eventType = eventType;
        this.timetable = timetable;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters & Setters
    public TimetableEventType getEventType() {
        return eventType;
    }

    public void setEventType(TimetableEventType eventType) {
        this.eventType = eventType;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
