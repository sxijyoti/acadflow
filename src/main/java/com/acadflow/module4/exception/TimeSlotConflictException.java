package com.acadflow.module4.exception;

/**
 * Exception thrown when a time slot conflict is detected
 */
public class TimeSlotConflictException extends RuntimeException {

    private String conflictDetails;

    public TimeSlotConflictException(String message) {
        super(message);
    }

    public TimeSlotConflictException(String message, String conflictDetails) {
        super(message);
        this.conflictDetails = conflictDetails;
    }

    public TimeSlotConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getConflictDetails() {
        return conflictDetails;
    }

    public void setConflictDetails(String conflictDetails) {
        this.conflictDetails = conflictDetails;
    }
}
