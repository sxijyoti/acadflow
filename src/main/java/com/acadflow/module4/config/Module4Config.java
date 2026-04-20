package com.acadflow.module4.config;

import com.acadflow.module4.service.EventPublisher;
import com.acadflow.module4.service.ExamService;
import com.acadflow.module4.service.TimetableService;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

/**
 * Configuration class for Module 4
 * Sets up the Observer Pattern by registering listeners
 * Demonstrates Dependency Inversion Principle
 */
@Configuration
public class Module4Config {

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private ExamService examService;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Initialize the Observer Pattern
     * Register EventPublisher as a listener for both Exam and Timetable events
     */
    @PostConstruct
    public void initializeObservers() {
        // Register EventPublisher as listener for exam events
        // This ensures exams are automatically added to the calendar
        examService.addListener(eventPublisher);

        // Register EventPublisher as listener for timetable events
        // Future hook for calendar integration features
        timetableService.addListener(eventPublisher);
    }
}
