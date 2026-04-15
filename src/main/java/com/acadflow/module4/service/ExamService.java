package com.acadflow.module4.service;

import com.acadflow.module4.entity.Exam;
import com.acadflow.module4.dto.ExamCreateDTO;
import com.acadflow.module4.dto.ExamResponseDTO;
import com.acadflow.module4.event.ExamEvent;
import com.acadflow.module4.event.ExamEventListener;
import com.acadflow.module4.exception.ResourceNotFoundException;
import com.acadflow.module4.repository.ExamRepository;
import com.acadflow.module4.util.ConflictValidator;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing exams
 * Implements Observer Pattern for notifying listeners of exam changes
 */
@Service
@Transactional
public class ExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;

    // Observer listeners
    private final List<ExamEventListener> listeners = new ArrayList<>();

    public ExamService(
            ExamRepository examRepository,
            SubjectRepository subjectRepository) {
        this.examRepository = examRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Add a listener for exam events - Observer Pattern
     */
    public void addListener(ExamEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener for exam events
     */
    public void removeListener(ExamEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners of an exam event
     */
    private void notifyListeners(ExamEvent event) {
        switch (event.getEventType()) {
            case CREATED:
                listeners.forEach(listener -> listener.onExamCreated(event));
                break;
            case UPDATED:
                listeners.forEach(listener -> listener.onExamUpdated(event));
                break;
            case DELETED:
                listeners.forEach(listener -> listener.onExamDeleted(event));
                break;
        }
    }

    /**
     * Create a new exam
     * @param dto the exam creation DTO
     * @return the created exam response DTO
     * @throws ResourceNotFoundException if subject not found
     */
    public ExamResponseDTO createExam(ExamCreateDTO dto) {
        // Find the subject
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found",
                    "Subject",
                    dto.getSubjectId()
                ));

        // Validate exam date
        ConflictValidator.validateExamDate(dto.getDate());

        // Create exam
        Exam exam = new Exam(
            subject,
            dto.getDate(),
            dto.getType(),
            dto.getLocation()
        );
        exam.setInstructions(dto.getInstructions());
        exam.setMaxMarks(dto.getMaxMarks());
        exam.setDuration(dto.getDuration());

        // Save and notify
        Exam saved = examRepository.save(exam);
        notifyListeners(new ExamEvent(ExamEvent.ExamEventType.CREATED, saved));

        return mapToResponseDTO(saved);
    }

    /**
     * Get all exams for a subject
     */
    public List<ExamResponseDTO> getExamsForSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found",
                    "Subject",
                    subjectId
                ));

        List<Exam> exams = examRepository.findBySubject(subject);
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get upcoming exams within a date range
     */
    public List<ExamResponseDTO> getUpcomingExams(LocalDateTime startDate, LocalDateTime endDate) {
        List<Exam> exams = examRepository.findByDateBetween(startDate, endDate);
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific exam
     */
    public ExamResponseDTO getExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Exam not found",
                    "Exam",
                    id
                ));
        return mapToResponseDTO(exam);
    }

    /**
     * Get exam details
     */
    public List<ExamResponseDTO> getExamDetails(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found",
                    "Subject",
                    subjectId
                ));

        List<Exam> exams = examRepository.findBySubject(subject);
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an exam
     */
    public ExamResponseDTO updateExam(Long id, ExamCreateDTO dto) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Exam not found",
                    "Exam",
                    id
                ));

        // Validate exam date if changed
        if (!exam.getDate().equals(dto.getDate())) {
            ConflictValidator.validateExamDate(dto.getDate());
            exam.setDate(dto.getDate());
        }

        // Update fields
        exam.setType(dto.getType());
        exam.setLocation(dto.getLocation());
        exam.setInstructions(dto.getInstructions());
        exam.setMaxMarks(dto.getMaxMarks());
        exam.setDuration(dto.getDuration());

        // Save and notify
        Exam updated = examRepository.save(exam);
        notifyListeners(new ExamEvent(ExamEvent.ExamEventType.UPDATED, updated));

        return mapToResponseDTO(updated);
    }

    /**
     * Delete an exam
     */
    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Exam not found",
                    "Exam",
                    id
                ));

        examRepository.deleteById(id);
        notifyListeners(new ExamEvent(ExamEvent.ExamEventType.DELETED, exam));
    }

    /**
     * Get all upcoming exams after today
     */
    public List<ExamResponseDTO> getAllUpcomingExams() {
        List<Exam> exams = examRepository.findByDateAfter(LocalDateTime.now());
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all exams ordered by date
     */
    public List<ExamResponseDTO> getAllExamsOrdered() {
        List<Exam> exams = examRepository.findAllByOrderByDateAsc();
        return exams.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map entity to response DTO
     */
    private ExamResponseDTO mapToResponseDTO(Exam exam) {
        ExamResponseDTO dto = new ExamResponseDTO();
        dto.setId(exam.getId());
        dto.setSubjectId(exam.getSubject().getId());
        dto.setSubjectName(exam.getSubject().getName());
        dto.setSubjectCode(exam.getSubject().getCode());
        dto.setDate(exam.getDate());
        dto.setType(exam.getType());
        dto.setLocation(exam.getLocation());
        dto.setInstructions(exam.getInstructions());
        dto.setMaxMarks(exam.getMaxMarks());
        dto.setDuration(exam.getDuration());
        return dto;
    }
}
