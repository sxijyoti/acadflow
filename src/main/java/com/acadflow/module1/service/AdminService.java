package com.acadflow.module1.service;

import com.acadflow.module1.dto.SubjectCreateDTO;
import com.acadflow.module1.entity.Role;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public AdminService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public Subject createSubject(Long adminId, SubjectCreateDTO dto) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));
        
        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create subjects");
        }
        
        Subject subject = new Subject();
        subject.setName(dto.name);
        subject.setCode(dto.code);
        subject.setCredits(dto.credits);
        
        if (dto.instructorId != null) {
            User instructor = userRepository.findById(dto.instructorId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            subject.setInstructor(instructor);
        }
        
        return subjectRepository.save(subject);
    }
}
