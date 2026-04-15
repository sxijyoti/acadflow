package com.acadflow.ui.dto;

import java.time.LocalTime;

/**
 * Timetable Slot DTO
 */
public class TimetableSlotDTO {
    private Long id;
    private String dayOfWeek; // MONDAY, TUESDAY, etc.
    private LocalTime startTime;
    private LocalTime endTime;
    private String subjectCode;
    private String subjectName;
    private String location;
    private String instructor;

    public TimetableSlotDTO() {}

    public TimetableSlotDTO(String dayOfWeek, LocalTime startTime, LocalTime endTime, String subjectCode, String subjectName) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
}
