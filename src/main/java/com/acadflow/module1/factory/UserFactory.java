package com.acadflow.module1.factory;

import com.acadflow.module1.entity.User;
import com.acadflow.module1.entity.Role;

public class UserFactory {
    public static User createUser(Role role, String name, String email, String department, Integer semester) {
        User user = new User();
        user.setRole(role);
        
        // Parse full name into first and last name
        if (name != null && !name.isEmpty()) {
            String[] nameParts = name.trim().split(" ", 2);
            user.setFirstName(nameParts[0]);
            if (nameParts.length > 1) {
                user.setLastName(nameParts[1]);
            }
        }
        
        user.setEmail(email);
        user.setDepartment(department);
        
        if (role == Role.STUDENT) {
            user.setSemester(semester);
        }
        return user;
    }
}
