package com.acadflow.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.entity.Subject;

import java.util.Optional;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserAndSubject(User user, Subject subject);
    List<Enrollment> findByUser(User user);
}
