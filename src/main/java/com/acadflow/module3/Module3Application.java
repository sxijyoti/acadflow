package com.acadflow.module3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Module 3: Calendar & Event Management
 * 
 * Features:
 * - Unified Calendar View (aggregates assignments, exams, holidays)
 * - Holiday Management
 * - Event Filtering (by type, date range, importance)
 * 
 * Design Patterns:
 * - Adapter Pattern: Convert different event types (Assignment, Holiday) to unified Event format
 * 
 * Design Principles:
 * - Interface Segregation Principle (ISP): Separate EventProvider and HolidayProvider interfaces
 * 
 * REST Endpoints:
 * - /api/v1/calendar/* - Calendar event management
 * - /api/v1/holidays/* - Holiday management
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.acadflow.module1",
    "com.acadflow.module2",
    "com.acadflow.module3"
})
public class Module3Application {

    public static void main(String[] args) {
        SpringApplication.run(Module3Application.class, args);
    }
}
