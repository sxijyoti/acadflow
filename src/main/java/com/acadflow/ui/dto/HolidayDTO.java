package com.acadflow.ui.dto;

import java.time.LocalDate;

/**
 * Holiday DTO
 */
public class HolidayDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private String category; // PUBLIC, RELIGIOUS, ACADEMIC

    public HolidayDTO() {}

    public HolidayDTO(Long id, String name, LocalDate date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
