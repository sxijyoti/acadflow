package com.acadflow.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acadflow.module1.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
