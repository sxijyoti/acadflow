package com.acadflow.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acadflow.module1.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
