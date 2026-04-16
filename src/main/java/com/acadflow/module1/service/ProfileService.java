package com.acadflow.module1.service;

import com.acadflow.module1.entity.User;
import com.acadflow.module1.repository.UserRepository;
import com.acadflow.module1.dto.UserCreateDTO;
import com.acadflow.module1.entity.Role;
import com.acadflow.module1.factory.UserFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createProfile(UserCreateDTO dto) {
        Role role = Role.valueOf(dto.role.toUpperCase());
        User user = UserFactory.createUser(role, dto.name, dto.email, dto.department, dto.semester);
        return userRepository.save(user);
    }

    public Optional<User> getProfile(Long id) {
        return userRepository.findById(id);
    }

    public User updateProfile(Long id, UserCreateDTO dto) {
        return userRepository.findById(id).map(user -> {
            if (dto.name != null && !dto.name.isEmpty()) {
                String[] nameParts = dto.name.trim().split(" ", 2);
                user.setFirstName(nameParts[0]);
                if (nameParts.length > 1) {
                    user.setLastName(nameParts[1]);
                }
            }
            if (dto.department != null) user.setDepartment(dto.department);
            if (dto.semester != null) user.setSemester(dto.semester);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
