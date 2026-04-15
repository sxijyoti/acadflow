package com.acadflow.module3.dto;

import java.time.LocalDate;

/**
 * DTO for creating/updating holidays
 * Used in API requests to create or modify holiday data
 */
public class HolidayCreateDTO {
    private String name;
    private LocalDate date;
    private String type;

    // Constructors
    public HolidayCreateDTO() {}

    public HolidayCreateDTO(String name, LocalDate date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
