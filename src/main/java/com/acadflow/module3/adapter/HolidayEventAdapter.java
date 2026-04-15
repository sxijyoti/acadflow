package com.acadflow.module3.adapter;

import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;
import com.acadflow.module3.entity.Holiday;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * ADAPTER PATTERN: Converts Holiday to unified Event format
 * Adapts Holiday interface to unified Event format for calendar display
 */
public class HolidayEventAdapter {

    /**
     * Converts a Holiday to a unified Event
     * @param holiday the holiday to convert
     * @return an Event representing the holiday
     */
    public static Event convertHolidayToEvent(Holiday holiday) {
        Event event = new Event();
        event.setTitle("Holiday: " + holiday.getName());
        event.setType(EventType.HOLIDAY);
        // Convert LocalDate to LocalDateTime (midnight)
        event.setDate(holiday.getDate().atTime(LocalTime.MIDNIGHT));
        event.setDescription("Type: " + holiday.getType());
        event.setIsImportant(true); // Holidays are always important
        event.setSubject(null); // Holidays are not subject-specific
        return event;
    }
}
