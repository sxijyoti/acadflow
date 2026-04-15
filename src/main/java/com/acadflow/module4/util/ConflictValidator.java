package com.acadflow.module4.util;

import com.acadflow.module4.entity.Timetable;
import com.acadflow.module4.exception.TimeSlotConflictException;
import java.util.List;

/**
 * Utility class for validating timetable conflicts
 */
public class ConflictValidator {

    /**
     * Check if a timetable slot conflicts with existing slots
     * @param newSlot the new timetable slot to check
     * @param existingSlots the existing timetable slots to check against
     * @return true if no conflict, false if conflict exists
     * @throws TimeSlotConflictException if a conflict is detected
     */
    public static void validateNoConflict(Timetable newSlot, List<Timetable> existingSlots) 
            throws TimeSlotConflictException {
        
        for (Timetable existingSlot : existingSlots) {
            if (newSlot.overlapsWith(existingSlot)) {
                String conflictDetails = String.format(
                    "Conflict with %s on %s from %s to %s",
                    existingSlot.getSubject().getName(),
                    existingSlot.getDay(),
                    existingSlot.getStartTime(),
                    existingSlot.getEndTime()
                );
                throw new TimeSlotConflictException(
                    "Time slot conflict detected", 
                    conflictDetails
                );
            }
        }
    }

    /**
     * Check if two time slots overlap
     * @param startTime1 start time of first slot
     * @param endTime1 end time of first slot
     * @param startTime2 start time of second slot
     * @param endTime2 end time of second slot
     * @return true if slots overlap, false otherwise
     */
    public static boolean hasTimeOverlap(
            java.time.LocalTime startTime1, 
            java.time.LocalTime endTime1,
            java.time.LocalTime startTime2, 
            java.time.LocalTime endTime2) {
        return !(endTime1.isBefore(startTime2) || startTime1.isAfter(endTime2));
    }

    /**
     * Validate timetable creation parameters
     * @param newSlot the timetable slot to validate
     * @throws IllegalArgumentException if validation fails
     */
    public static void validateTimetableSlot(Timetable newSlot) throws IllegalArgumentException {
        if (newSlot.getSubject() == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        if (newSlot.getDay() == null) {
            throw new IllegalArgumentException("Day cannot be null");
        }

        if (newSlot.getStartTime() == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }

        if (newSlot.getEndTime() == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }

        if (newSlot.getEndTime().isBefore(newSlot.getStartTime()) || 
            newSlot.getEndTime().equals(newSlot.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Validate reasonable duration (e.g., max 6 hours)
        java.time.Duration duration = java.time.Duration.between(newSlot.getStartTime(), newSlot.getEndTime());
        if (duration.toHours() > 6) {
            throw new IllegalArgumentException("Class duration cannot exceed 6 hours");
        }

        // Validate class is not less than 15 minutes
        if (duration.toMinutes() < 15) {
            throw new IllegalArgumentException("Class duration must be at least 15 minutes");
        }
    }

    /**
     * Validate exam creation parameters
     * @param examDate the exam date to validate
     * @throws IllegalArgumentException if validation fails
     */
    public static void validateExamDate(java.time.LocalDateTime examDate) throws IllegalArgumentException {
        if (examDate == null) {
            throw new IllegalArgumentException("Exam date cannot be null");
        }

        if (examDate.isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Exam date cannot be in the past");
        }

        // Validate exam is at least 24 hours in future
        java.time.LocalDateTime tomorrow = java.time.LocalDateTime.now().plusDays(1);
        if (examDate.isBefore(tomorrow)) {
            throw new IllegalArgumentException("Exam must be scheduled at least 24 hours in advance");
        }
    }
}
