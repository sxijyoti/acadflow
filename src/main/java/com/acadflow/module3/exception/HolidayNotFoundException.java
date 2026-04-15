package com.acadflow.module3.exception;

/**
 * Exception thrown when a holiday is not found
 */
public class HolidayNotFoundException extends CalendarException {
    
    public HolidayNotFoundException(Long id) {
        super("Holiday with ID " + id + " not found");
    }

    public HolidayNotFoundException(String message) {
        super(message);
    }
}
