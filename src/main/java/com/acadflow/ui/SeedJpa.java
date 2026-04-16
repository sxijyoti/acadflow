package com.acadflow.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.acadflow.module1.Module1Application;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.entity.Role;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.entity.EnrollmentStatus;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module1.repository.UserRepository;
import com.acadflow.module1.repository.EnrollmentRepository;
import com.acadflow.module2.repository.AssignmentRepository;
import java.time.LocalDateTime;
import java.util.List;

public class SeedJpa {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Module1Application.class, args);
        SubjectRepository sRepo = ctx.getBean(SubjectRepository.class);
        UserRepository uRepo = ctx.getBean(UserRepository.class);
        EnrollmentRepository eRepo = ctx.getBean(EnrollmentRepository.class);
        AssignmentRepository aRepo = ctx.getBean(AssignmentRepository.class);
        
        try {
            System.out.println("Seeding via JPA...");
            
            // Get valid subject with instructor just in case
            List<Subject> subjects = sRepo.findAll();
            Subject s1;
            User inst;
            List<User> instructors = uRepo.findAll().stream().filter(u -> u.getRole() == Role.INSTRUCTOR || u.getRole() == Role.ADMIN).toList();
            if (instructors.isEmpty()) {
                inst = new User();
                inst.setFirstName("TEACH"); inst.setLastName("ER");
                inst.setEmail("tea@cha.er"); inst.setPassword("t"); inst.setRole(Role.INSTRUCTOR);
                inst = uRepo.save(inst);
            } else {
                inst = instructors.get(0);
            }
            if (subjects.isEmpty()) {
                s1 = new Subject();
                s1.setName("Maths"); s1.setCode("MAT101"); s1.setCredits(4);
                s1.setInstructor(inst);
                s1 = sRepo.save(s1);
            } else {
                s1 = subjects.get(0);
            }

            List<User> students = uRepo.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).toList();
            if (students.isEmpty()) {
                System.out.println("No student found, creating one");
                User u = new User();
                u.setEmail("student2@test.com");
                u.setFirstName("Stud2");
                u.setLastName("Ent2");
                u.setPassword("pass");
                u.setRole(Role.STUDENT);
                uRepo.save(u);
                students = List.of(u);
            }
            User student = students.get(0);
            
            var enrolls = eRepo.findByUserId(student.getId());
            if(enrolls.isEmpty()) {
                Enrollment e1 = new Enrollment();
                e1.setUser(student);
                e1.setSubject(s1);
                e1.setStatus(EnrollmentStatus.ENROLLED);
                eRepo.save(e1);
            }

            if(aRepo.count() == 0) {
                Assignment a1 = new Assignment();
                a1.setTitle("Calculus Homework");
                a1.setDescription("Do limits");
                Assignment a2 = new Assignment();
                a2.setTitle("Linear Algebra HW");
                a2.setDescription("Matrix ops");
                
                a1.setSubject(s1);
                a1.setDeadline(LocalDateTime.now().plusDays(5));
                aRepo.save(a1);
                
                a2.setSubject(s1);
                a2.setDeadline(LocalDateTime.now().plusDays(10));
                aRepo.save(a2);
            }
            
            System.out.println("Successfully seeded all data.");
            System.exit(0);
        } catch(Exception ex) {
            System.out.println("FAILED TO INSERT!");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
