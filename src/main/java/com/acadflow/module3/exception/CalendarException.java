package com.acadflow.module3.exception;

/**
 * Base exception for Module 3 Calendar exceptions
 */
public class CalendarException extends RuntimeException {
    
    public CalendarException(String message) {
        super(message);
    }

    public CalendarException(String message, Throwable cause) {
        super(message, cause);
    }
}
