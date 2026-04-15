package com.acadflow.module3.service;

import com.acadflow.module3.adapter.HolidayEventAdapter;
import com.acadflow.module3.adapter.HolidayProvider;
import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.Holiday;
import com.acadflow.module3.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing holidays
 * Implements HolidayProvider interface for providing holiday events
 * Converts holidays to unified event format for calendar display
 */
@Service
public class HolidayService implements HolidayProvider {

    @Autowired
    private HolidayRepository holidayRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    /**
     * Get all holidays as events
     * @return List of holiday events
     */
    @Override
    public List<Event> getHolidays() {
        List<Holiday> holidays = holidayRepository.findAll();
        return holidays.stream()
                .map(HolidayEventAdapter::convertHolidayToEvent)
                .collect(Collectors.toList());
    }

    /**
     * Get holidays within a specific date range
     * @param startDate the start date (ISO format: YYYY-MM-DD)
     * @param endDate the end date (ISO format: YYYY-MM-DD)
     * @return List of holiday events in the range
     */
    @Override
    public List<Event> getHolidaysByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
        List<Holiday> holidays = holidayRepository.findByDateBetween(start, end);
        return holidays.stream()
                .map(HolidayEventAdapter::convertHolidayToEvent)
                .collect(Collectors.toList());
    }

    /**
     * Get holidays by type
     * @param type the holiday type
     * @return List of holidays of that type
     */
    public List<Event> getHolidaysByType(String type) {
        List<Holiday> holidays = holidayRepository.findByType(type);
        return holidays.stream()
                .map(HolidayEventAdapter::convertHolidayToEvent)
                .collect(Collectors.toList());
    }

    /**
     * Get all raw Holiday objects
     * @return List of holidays
     */
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    /**
     * Get raw holidays within a date range
     * @param startDate the start date
     * @param endDate the end date
     * @return List of holidays in the range
     */
    public List<Holiday> getHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        return holidayRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * Create a new holiday
     * @param holiday the holiday to create
     * @return the created holiday
     */
    public Holiday createHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    /**
     * Update an existing holiday
     * @param id the holiday ID
     * @param holiday the updated holiday
     * @return the updated holiday
     */
    public Holiday updateHoliday(Long id, Holiday holiday) {
        holiday.setId(id);
        return holidayRepository.save(holiday);
    }

    /**
     * Delete a holiday
     * @param id the holiday ID
     */
    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }

    /**
     * Get holiday by ID
     * @param id the holiday ID
     * @return the holiday if found
     */
    public Holiday getHolidayById(Long id) {
        return holidayRepository.findById(id).orElse(null);
    }

    /**
     * Get holiday by name
     * @param name the holiday name
     * @return List of holidays with that name
     */
    public List<Holiday> getHolidaysByName(String name) {
        return holidayRepository.findByName(name);
    }

    /**
     * Check if a specific date is a holiday
     * @param date the date to check
     * @return true if the date is a holiday
     */
    public boolean isHoliday(LocalDate date) {
        List<Holiday> holidays = holidayRepository.findByDateBetween(date, date);
        return !holidays.isEmpty();
    }
}
