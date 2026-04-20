package com.acadflow.module3.exception;

/**
 * Exception thrown when invalid date range is provided
 */
public class InvalidDateRangeException extends CalendarException {
    
    public InvalidDateRangeException(String startDate, String endDate) {
        super("Invalid date range: start date '" + startDate + "' must be before end date '" + endDate + "'");
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
