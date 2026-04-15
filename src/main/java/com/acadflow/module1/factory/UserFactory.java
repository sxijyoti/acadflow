package com.acadflow.module1.factory;

import com.acadflow.module1.entity.User;
import com.acadflow.module1.entity.Role;

public class UserFactory {
    public static User createUser(Role role, String name, String email, String department, Integer semester) {
        User user = new User();
        user.setRole(role);
        user.setName(name);
        user.setEmail(email);
        user.setDepartment(department);
        
        if (role == Role.STUDENT) {
            user.setSemester(semester);
        }
        return user;
    }
}
