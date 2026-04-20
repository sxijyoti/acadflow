package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import com.acadflow.module1.service.EnrollmentService;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module1.entity.EnrollmentStatus;
import java.util.stream.Collectors;

public class TestDb {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        EnrollmentService service = ctx.getBean(EnrollmentService.class);
        SubjectRepository repo = ctx.getBean(SubjectRepository.class);
        
        System.out.println("Total subjects: " + repo.findAll().size());
        var enrolls = service.getEnrolledSubjects(4L);
        System.out.println("Total enrollments for user 4: " + enrolls.size());
        
        System.exit(0);
    }
}
