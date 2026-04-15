package com.acadflow.module1.repository;

import com.acadflow.module1.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    //  needed for AssignmentService
    List<Enrollment> findByUserId(Long userId);

    //  needed for SubmissionService
    Optional<Enrollment> findByUserIdAndSubjectId(Long userId, Long subjectId);
}