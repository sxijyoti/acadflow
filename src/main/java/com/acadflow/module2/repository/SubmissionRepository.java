package com.acadflow.module2.repository;

import com.acadflow.module2.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // Get all submissions of a user
    List<Submission> findByUserId(Long userId);

    // Prevent duplicate submissions
    Submission findByUserIdAndAssignmentId(Long userId, Long assignmentId);
}