package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class SeedJdbc {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        JdbcTemplate jdbc = ctx.getBean(JdbcTemplate.class);
        
        try {
            System.out.println("Seeding more explicit database data...");
            
            jdbc.execute("SET FOREIGN_KEY_CHECKS = 0;");
            
            // Add holidays
            jdbc.update("INSERT IGNORE INTO holidays (id, name, date, type) VALUES (11, 'Spring Festival', ?, 'Event');", LocalDate.now().plusDays(5));
            jdbc.update("INSERT IGNORE INTO holidays (id, name, date, type) VALUES (12, 'National Day', ?, 'National');", LocalDate.now().plusDays(10));
            jdbc.update("INSERT IGNORE INTO holidays (id, name, date, type) VALUES (13, 'End of Term Break', ?, 'Break');", LocalDate.now().plusDays(20));

            // Add exams (assuming subject 1 and 2 exist and user is enrolled in them)
            jdbc.update("INSERT IGNORE INTO exams (id, date, duration, instructions, location, max_marks, type, subject_id) VALUES (11, ?, 90, 'Bring ID', 'Room 101', 50, 'QUIZ', 1);", LocalDateTime.now().plusDays(6));
            jdbc.update("INSERT IGNORE INTO exams (id, date, duration, instructions, location, max_marks, type, subject_id) VALUES (12, ?, 180, 'Open book', 'Hall B', 100, 'FINAL', 1);", LocalDateTime.now().plusDays(25));
                        
            System.out.println("Extra seeding successful.");
            System.exit(0);
        } catch(Exception ex) {
            System.out.println("FAILED TO INSERT!");
            ex.printStackTrace();
            System.exit(1);
        } finally {
            jdbc.execute("SET FOREIGN_KEY_CHECKS = 1;");
        }
    }
}
