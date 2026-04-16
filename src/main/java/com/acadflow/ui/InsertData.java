package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.repository.AssignmentRepository;
import com.acadflow.module1.repository.SubjectRepository;

import java.time.LocalDateTime;

public class InsertData {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        
        AssignmentRepository assignmentRepo = ctx.getBean(AssignmentRepository.class);
        SubjectRepository subjectRepo = ctx.getBean(SubjectRepository.class);

        try {
            System.out.println("Inserting dummy assignments using JPA...");
            
            Assignment a1 = new Assignment();
            a1.setTitle("Calculus HW 1");
            a1.setDescription("Complete exercises 1-15 on limits.");
            a1.setSubject(subjectRepo.findById(1L).orElseThrow());
            a1.setDeadline(LocalDateTime.now().plusDays(5));
            assignmentRepo.save(a1);

            Assignment a2 = new Assignment();
            a2.setTitle("Linear Algebra Project");
            a2.setDescription("Create a matrix operations solver.");
            a2.setSubject(subjectRepo.findById(1L).orElseThrow());
            a2.setDeadline(LocalDateTime.now().plusDays(14));
            assignmentRepo.save(a2);

            Assignment a3 = new Assignment();
            a3.setTitle("Physics Lab 1");
            a3.setDescription("Report on pendulum oscillation experiment.");
            a3.setSubject(subjectRepo.findById(2L).orElseThrow());
            a3.setDeadline(LocalDateTime.now().plusDays(2));
            assignmentRepo.save(a3);
            
            System.out.println("Assignments insertion complete!");
            System.exit(0);
        } catch(Exception ex) {
            System.out.println("FAILED TO INSERT!");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
