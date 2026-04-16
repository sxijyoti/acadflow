package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import com.acadflow.module2.service.AssignmentService;
import com.acadflow.module2.service.SubmissionService;

public class TestSubmission {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        try {
            AssignmentService assignmentService = ctx.getBean(AssignmentService.class);
            SubmissionService submissionService = ctx.getBean(SubmissionService.class);
            
            System.out.println("Got services! Testing assignment fetch...");
            // We just want to check if the context loads and beans are available as a quick smoketest
            System.out.println("Test Complete! Exiting...");
            System.exit(0);
        } catch(Exception ex) {
            System.out.println("TEST FAILED!");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
