package com.acadflow.module3.exception;

/**
 * Exception thrown when an event is not found
 */
public class EventNotFoundException extends CalendarException {
    
    public EventNotFoundException(Long id) {
        super("Event with ID " + id + " not found");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
