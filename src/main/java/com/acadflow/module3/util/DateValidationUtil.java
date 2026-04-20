package com.acadflow.module3.util;

import com.acadflow.module3.exception.InvalidDateRangeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class for date validations and operations
 */
public class DateValidationUtil {

    /**
     * Validate that start date is before end date
     * @param startDate the start date
     * @param endDate the end date
     * @throws InvalidDateRangeException if startDate is after endDate
     */
    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException(startDate.toString(), endDate.toString());
        }
    }

    /**
     * Validate that start datetime is before end datetime
     * @param startDate the start datetime
     * @param endDate the end datetime
     * @throws InvalidDateRangeException if startDate is after endDate
     */
    public static void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException(startDate.toString(), endDate.toString());
        }
    }

    /**
     * Check if a date falls within a range
     * @param date the date to check
     * @param startDate the start of range
     * @param endDate the end of range
     * @return true if date is within range (inclusive)
     */
    public static boolean isWithinRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Check if a datetime falls within a range
     * @param dateTime the datetime to check
     * @param startDate the start of range
     * @param endDate the end of range
     * @return true if datetime is within range (inclusive)
     */
    public static boolean isWithinRange(LocalDateTime dateTime, LocalDateTime startDate, LocalDateTime endDate) {
        return !dateTime.isBefore(startDate) && !dateTime.isAfter(endDate);
    }

    /**
     * Get the number of days between two dates (inclusive)
     * @param startDate the start date
     * @param endDate the end date
     * @return number of days between dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    /**
     * Check if a date is a weekend (Saturday or Sunday)
     * @param date the date to check
     * @return true if date is Saturday or Sunday
     */
    public static boolean isWeekend(LocalDate date) {
        java.time.DayOfWeek day = date.getDayOfWeek();
        return day == java.time.DayOfWeek.SATURDAY || day == java.time.DayOfWeek.SUNDAY;
    }

    /**
     * Check if a date is a weekday (Monday to Friday)
     * @param date the date to check
     * @return true if date is a weekday
     */
    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }
}
