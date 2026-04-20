package com.acadflow.module1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.repository.UserRepository;
import java.util.Optional;

/**
 * Service for user authentication and profile management
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Authenticate user with email and password
     * @param email User email
     * @param password User password
     * @return Optional containing User if authentication successful, empty otherwise
     */
    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        
        if (user.isPresent()) {
            // Simple password comparison (in production, use proper hashing like BCrypt)
            if (user.get().getPassword() != null && 
                user.get().getPassword().equals(password)) {
                return user;
            }
        }
        return Optional.empty();
    }
    
    /**
     * Get user by ID
     * @param id User ID
     * @return Optional containing User if found, empty otherwise
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Get user by email
     * @param email User email
     * @return Optional containing User if found, empty otherwise
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Save or update user
     * @param user User entity to save
     * @return Saved user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
