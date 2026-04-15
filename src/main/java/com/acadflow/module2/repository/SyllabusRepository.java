package com.acadflow.module2.repository;

import com.acadflow.module2.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

    Optional<Syllabus> findBySubjectId(Long subjectId);
}