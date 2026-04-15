package com.acadflow.module2.service;

import com.acadflow.module1.entity.User;
import com.acadflow.module1.repository.UserRepository;
import com.acadflow.module2.dto.SubmissionResponseDTO;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.entity.Submission;
import com.acadflow.module2.entity.SubmissionStatus;
import com.acadflow.module2.repository.AssignmentRepository;
import com.acadflow.module2.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             AssignmentRepository assignmentRepository,
                             UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    // 🔥 CREATE SUBMISSION (FINAL VERSION)
    public Submission create(Long assignmentId, Long userId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 DUPLICATE CHECK
        Submission existing = submissionRepository
                .findByUserIdAndAssignmentId(userId, assignmentId);

        if (existing != null) {
            throw new RuntimeException("Already submitted this assignment");
        }

        Submission submission = new Submission();

        submission.setAssignment(assignment);
        submission.setUser(user);   // ✅ IMPORTANT
        submission.setSubmittedAt(LocalDateTime.now());

        // 🔥 STATUS LOGIC
        if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
            submission.setStatus(SubmissionStatus.LATE);
        } else {
            submission.setStatus(SubmissionStatus.SUBMITTED);
        }

        return submissionRepository.save(submission);
    }

    // 🔥 GET SUBMISSIONS BY USER
    public List<SubmissionResponseDTO> getByUser(Long userId) {

        List<Submission> submissions = submissionRepository.findByUserId(userId);

        return submissions.stream().map(s -> {
            SubmissionResponseDTO dto = new SubmissionResponseDTO();

            dto.id = s.getId();
            dto.assignmentId = s.getAssignment().getId();
            dto.userId = s.getUser().getId();
            dto.status = s.getStatus().name();
            dto.submittedAt = s.getSubmittedAt();

            return dto;
        }).collect(Collectors.toList());
    }
}