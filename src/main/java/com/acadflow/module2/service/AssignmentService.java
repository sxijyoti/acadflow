package com.acadflow.module2.service;

import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.repository.AssignmentRepository;

import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module1.repository.EnrollmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository repo;
    private final SubjectRepository subjectRepo;
    private final EnrollmentRepository enrollmentRepo;

    public AssignmentService(AssignmentRepository repo,
                             SubjectRepository subjectRepo,
                             EnrollmentRepository enrollmentRepo) {
        this.repo = repo;
        this.subjectRepo = subjectRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public Assignment create(Assignment assignment, Long subjectId) {

        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        assignment.setSubject(subject);
        return repo.save(assignment);
    }

    public List<Assignment> getAssignmentsForStudent(Long userId) {

        List<Long> subjectIds = enrollmentRepo.findByUserId(userId)
                .stream()
                .map(e -> e.getSubject().getId())
                .toList();

        return repo.findAll()
                .stream()
                .filter(a -> subjectIds.contains(a.getSubject().getId()))
                .toList();
    }

    public List<Assignment> getAssignmentsForInstructor(Long userId) {
        return repo.findAll()
                .stream()
                .filter(a -> a.getSubject() != null && a.getSubject().getInstructor() != null && a.getSubject().getInstructor().getId().equals(userId))
                .toList();
    }
}