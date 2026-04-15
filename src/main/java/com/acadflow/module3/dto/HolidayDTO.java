package com.acadflow.module3.dto;

import java.time.LocalDate;

/**
 * DTO for Holiday responses
 * Used in API responses to expose holiday data
 */
public class HolidayDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String type;

    // Constructors
    public HolidayDTO() {}

    public HolidayDTO(Long id, String name, LocalDate date, String type) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
