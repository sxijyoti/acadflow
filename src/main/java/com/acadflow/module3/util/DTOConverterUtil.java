package com.acadflow.module3.util;

import com.acadflow.module3.dto.EventDTO;
import com.acadflow.module3.dto.HolidayDTO;
import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.Holiday;

/**
 * Utility class for converting entities to DTOs
 */
public class DTOConverterUtil {

    /**
     * Convert Event entity to EventDTO
     * @param event the event entity
     * @return EventDTO
     */
    public static EventDTO convertEventToDTO(Event event) {
        if (event == null) {
            return null;
        }
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setType(event.getType());
        dto.setDate(event.getDate());
        dto.setDescription(event.getDescription());
        dto.setIsImportant(event.getIsImportant());
        if (event.getSubject() != null) {
            dto.setSubjectId(event.getSubject().getId());
            dto.setSubjectName(event.getSubject().getName());
        }
        return dto;
    }

    /**
     * Convert Holiday entity to HolidayDTO
     * @param holiday the holiday entity
     * @return HolidayDTO
     */
    public static HolidayDTO convertHolidayToDTO(Holiday holiday) {
        if (holiday == null) {
            return null;
        }
        HolidayDTO dto = new HolidayDTO();
        dto.setId(holiday.getId());
        dto.setName(holiday.getName());
        dto.setDate(holiday.getDate());
        dto.setType(holiday.getType());
        return dto;
    }

    /**
     * Check if event has all required fields
     * @param event the event to validate
     * @return true if event is valid
     */
    public static boolean isValidEvent(Event event) {
        return event != null 
            && event.getTitle() != null && !event.getTitle().trim().isEmpty()
            && event.getType() != null
            && event.getDate() != null;
    }

    /**
     * Check if holiday has all required fields
     * @param holiday the holiday to validate
     * @return true if holiday is valid
     */
    public static boolean isValidHoliday(Holiday holiday) {
        return holiday != null
            && holiday.getName() != null && !holiday.getName().trim().isEmpty()
            && holiday.getDate() != null
            && holiday.getType() != null && !holiday.getType().trim().isEmpty();
    }
}
