package com.acadflow.ui.dto;

/**
 * Event DTO - represents events on calendar
 */
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private String eventType; // ASSIGNMENT, EXAM, HOLIDAY, CLASS
    private String date;
    private String color; // HEX color code or color name

    public EventDTO() {}

    public EventDTO(String title, String eventType, String date, String color) {
        this.title = title;
        this.eventType = eventType;
        this.date = date;
        this.color = color;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
