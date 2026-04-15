package com.acadflow.module1.service;

import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.entity.EnrollmentStatus;
import com.acadflow.module1.entity.Role;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.repository.EnrollmentRepository;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public Enrollment enroll(Long userId, Long subjectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only STUDENT can enroll in subjects");
        }
        
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        Optional<Enrollment> existingOpt = enrollmentRepository.findByUserAndSubject(user, subject);
        
        if (existingOpt.isPresent()) {
            Enrollment existing = existingOpt.get();
            if (existing.getStatus() == EnrollmentStatus.ENROLLED) {
                throw new RuntimeException("Already enrolled in this subject");
            } else {
                existing.setStatus(EnrollmentStatus.ENROLLED);
                return enrollmentRepository.save(existing);
            }
        }
        
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setUser(user);
        newEnrollment.setSubject(subject);
        newEnrollment.setStatus(EnrollmentStatus.ENROLLED);
        
        return enrollmentRepository.save(newEnrollment);
    }
    
    public Enrollment drop(Long userId, Long subjectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
                
        Enrollment existing = enrollmentRepository.findByUserAndSubject(user, subject)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
                
        existing.setStatus(EnrollmentStatus.DROPPED);
        return enrollmentRepository.save(existing);
    }
    
    public List<Enrollment> getEnrolledSubjects(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        return enrollmentRepository.findByUser(user);
    }
}
