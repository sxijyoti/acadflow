package com.acadflow.ui.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTOs for UI Layer
 * These match the backend API responses
 */

// Subject DTO
public class SubjectDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String instructor;
    private int credits;
    private int totalClasses;
    private int attendedClasses;

    public SubjectDTO() {}

    public SubjectDTO(Long id, String code, String name, String description, String instructor, int credits) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.instructor = instructor;
        this.credits = credits;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getTotalClasses() { return totalClasses; }
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }

    public int getAttendedClasses() { return attendedClasses; }
    public void setAttendedClasses(int attendedClasses) { this.attendedClasses = attendedClasses; }
}
