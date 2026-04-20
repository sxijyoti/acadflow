package com.acadflow.module4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Module 4: Timetable & Exam Management (FINAL INTEGRATION LAYER)
 * 
 * Features:
 * - Timetable Management: Add slots, prevent time conflicts, view weekly timetable
 * - Exam Management: Add exams, view exam details
 * - Calendar Integration: Automatically push exam dates to Module 3 events
 * 
 * Design Patterns:
 * - Observer Pattern: TimetableEventListener, ExamEventListener for cross-module integration
 * - Dependency Inversion Principle (DIP): Use interfaces between modules (loose coupling)
 * 
 * REST Endpoints:
 * - /api/v1/timetable/* - Timetable management
 * - /api/v1/exams/* - Exam management
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.acadflow.module1",
    "com.acadflow.module2",
    "com.acadflow.module3",
    "com.acadflow.module4"
})
@EnableJpaRepositories(basePackages = {
    "com.acadflow.module1.repository",
    "com.acadflow.module2.repository",
    "com.acadflow.module3.repository",
    "com.acadflow.module4.repository"
})
@EntityScan(basePackages = {
    "com.acadflow.module1.entity",
    "com.acadflow.module2.entity",
    "com.acadflow.module3.entity",
    "com.acadflow.module4.entity"
})
public class Module4Application {

    public static void main(String[] args) {
        SpringApplication.run(Module4Application.class, args);
    }
}
