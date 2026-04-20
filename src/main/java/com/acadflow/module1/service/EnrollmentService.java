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

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             SubjectRepository subjectRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    //  ENROLL
    public Enrollment enroll(Long userId, Long subjectId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only STUDENT can enroll in subjects");
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        //  FIXED: use ID-based method
        Optional<Enrollment> existingOpt =
                enrollmentRepository.findByUserIdAndSubjectId(userId, subjectId);

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

    //  DROP
    public Enrollment drop(Long userId, Long subjectId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        //  FIXED
        Enrollment existing = enrollmentRepository
                .findByUserIdAndSubjectId(userId, subjectId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        existing.setStatus(EnrollmentStatus.DROPPED);
        return enrollmentRepository.save(existing);
    }

    //  GET ENROLLED SUBJECTS
    public List<Enrollment> getEnrolledSubjects(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  FIXED
        return enrollmentRepository.findByUserId(userId);
    }
}