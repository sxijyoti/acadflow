package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

public class CheckDb {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        JdbcTemplate jdbc = ctx.getBean(JdbcTemplate.class);
        try {
            System.out.println("Checking DB state...");
            
            checkTable(jdbc, "user");
            checkTable(jdbc, "subject");
            checkTable(jdbc, "enrollment");
            checkTable(jdbc, "assignment");
            checkTable(jdbc, "submission");
            
            System.exit(0);
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void checkTable(JdbcTemplate jdbc, String table) {
        try {
            Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
            System.out.println(table + " count: " + count);
        } catch (Exception e) {
            System.out.println(table + " table missing or error: " + e.getMessage());
        }
    }
}
