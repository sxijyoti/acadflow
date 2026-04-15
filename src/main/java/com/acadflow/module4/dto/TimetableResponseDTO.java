package com.acadflow.module4.dto;

import com.acadflow.module4.entity.DayOfWeek;
import java.time.LocalTime;

/**
 * DTO for responding with timetable slot information
 */
public class TimetableResponseDTO {

    private Long id;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String classroom;

    // Constructors
    public TimetableResponseDTO() {}

    public TimetableResponseDTO(Long id, Long subjectId, String subjectName, DayOfWeek day, 
                               LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
