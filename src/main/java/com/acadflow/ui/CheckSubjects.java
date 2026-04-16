package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

public class CheckSubjects {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        JdbcTemplate jdbc = ctx.getBean(JdbcTemplate.class);
        try {
            System.out.println("Checking subjects and users...");
            List<Map<String, Object>> subjects = jdbc.queryForList("SELECT * FROM subjects");
            for (Map<String, Object> sub : subjects) {
                System.out.println("Subject: " + sub);
            }
            List<Map<String, Object>> users = jdbc.queryForList("SELECT * FROM users");
            for (Map<String, Object> user : users) {
                System.out.println("User: " + user);
            }
            System.exit(0);
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
