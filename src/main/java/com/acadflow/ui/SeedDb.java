package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import org.springframework.jdbc.core.JdbcTemplate;

public class SeedDb {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        JdbcTemplate jdbc = ctx.getBean(JdbcTemplate.class);
        try {
            System.out.println("Seeding subjects...");
            jdbc.execute("INSERT IGNORE INTO subjects (name, code, credits, description) VALUES ('Mathematics', 'MATH101', 4, 'Math')");
            jdbc.execute("INSERT IGNORE INTO subjects (name, code, credits, description) VALUES ('Physics', 'PHYS101', 4, 'Physics')");
            jdbc.execute("INSERT IGNORE INTO subjects (name, code, credits, description) VALUES ('Computer Science', 'CS101', 4, 'CS')");

            System.out.println("Seeding enrollments...");
            jdbc.execute("INSERT IGNORE INTO enrollments (user_id, subject_id, status) VALUES (3, 1, 'ACTIVE')");
            jdbc.execute("INSERT IGNORE INTO enrollments (user_id, subject_id, status) VALUES (3, 2, 'ACTIVE')");
            jdbc.execute("INSERT IGNORE INTO enrollments (user_id, subject_id, status) VALUES (4, 1, 'ACTIVE')");
            jdbc.execute("INSERT IGNORE INTO enrollments (user_id, subject_id, status) VALUES (4, 2, 'ACTIVE')");
            
            System.out.println("Seeding assignments...");
            jdbc.execute("INSERT IGNORE INTO assignments (subject_id, title, description, deadline) VALUES " +
                "(1, 'Calculus HW 1', 'Limits.', DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY))");
            jdbc.execute("INSERT IGNORE INTO assignments (subject_id, title, description, deadline) VALUES " +
                "(1, 'Linear Algebra', 'Solver.', DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY))");
            jdbc.execute("INSERT IGNORE INTO assignments (subject_id, title, description, deadline) VALUES " +
                "(2, 'Physics Lab 1', 'Pendulum.', DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY))");

            System.out.println("Data insertion complete!");
            System.exit(0);
        } catch(Exception ex) {
            System.out.println("FAILED TO INSERT!");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
