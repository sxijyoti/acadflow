package com.acadflow.module2.service;

import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.repository.SubjectRepository;
import com.acadflow.module2.entity.Syllabus;
import com.acadflow.module2.repository.SyllabusRepository;
import org.springframework.stereotype.Service;

@Service
public class SyllabusService {

    private final SyllabusRepository repo;
    private final SubjectRepository subjectRepository;

    public SyllabusService(SyllabusRepository repo,
                           SubjectRepository subjectRepository) {
        this.repo = repo;
        this.subjectRepository = subjectRepository;
    }

    // 🔥 CREATE / UPDATE
    public Syllabus create(Long subjectId, String content) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Syllabus syllabus = repo.findBySubjectId(subjectId)
                .orElse(new Syllabus());

        syllabus.setSubject(subject);
        syllabus.setContent(content);

        return repo.save(syllabus);
    }

    // 🔥 GET
    public Syllabus get(Long subjectId) {
        return repo.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Syllabus not found"));
    }
}