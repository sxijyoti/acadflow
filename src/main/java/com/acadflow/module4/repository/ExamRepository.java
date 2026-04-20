package com.acadflow.module4.repository;

import com.acadflow.module4.entity.Exam;
import com.acadflow.module1.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    /**
     * Find all exams for a specific subject
     */
    List<Exam> findBySubject(Subject subject);

    /**
     * Find all exams within a date range
     */
    List<Exam> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all exams after a specific date (upcoming exams)
     */
    List<Exam> findByDateAfter(LocalDateTime date);

    /**
     * Find all exams for a subject of a specific type
     */
    List<Exam> findBySubjectAndType(Subject subject, String type);

    /**
     * Custom query to find exams by subject within a date range
     */
    @Query("SELECT e FROM Exam e WHERE e.subject = :subject AND e.date BETWEEN :startDate AND :endDate")
    List<Exam> findUpcomingExamsForSubject(
            @Param("subject") Subject subject,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find all exams ordered by date
     */
    List<Exam> findAllByOrderByDateAsc();
}
