package com.acadflow.module3.adapter;

import com.acadflow.module3.entity.Event;
import java.util.List;

/**
 * ISP: Separate interface for providing holidays
 * Segregated from EventProvider to follow Interface Segregation Principle
 */
public interface HolidayProvider {
    /**
     * Get all holidays
     * @return List of holiday events
     */
    List<Event> getHolidays();

    /**
     * Get holidays within a specific date range
     * @param startDate the start date (ISO format)
     * @param endDate the end date (ISO format)
     * @return List of holiday events in the range
     */
    List<Event> getHolidaysByDateRange(String startDate, String endDate);
}
