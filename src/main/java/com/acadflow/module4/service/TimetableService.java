package com.acadflow.module4.service;

import com.acadflow.module4.entity.Timetable;
import com.acadflow.module4.entity.DayOfWeek;
import com.acadflow.module4.dto.TimetableCreateDTO;
import com.acadflow.module4.dto.TimetableResponseDTO;
import com.acadflow.module4.event.TimetableEvent;
import com.acadflow.module4.event.TimetableEventListener;
import com.acadflow.module4.exception.ResourceNotFoundException;
import com.acadflow.module4.exception.TimeSlotConflictException;
import com.acadflow.module4.repository.TimetableRepository;
import com.acadflow.module4.util.ConflictValidator;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing timetable slots
 * Implements Observer Pattern for notifying listeners of timetable changes
 */
@Service
@Transactional
public class TimetableService {

    private final TimetableRepository timetableRepository;
    private final SubjectRepository subjectRepository;

    // Observer listeners
    private final List<TimetableEventListener> listeners = new ArrayList<>();

    public TimetableService(
            TimetableRepository timetableRepository,
            SubjectRepository subjectRepository) {
        this.timetableRepository = timetableRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     * Add a listener for timetable events - Observer Pattern
     */
    public void addListener(TimetableEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener for timetable events
     */
    public void removeListener(TimetableEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners of a timetable event
     */
    private void notifyListeners(TimetableEvent event) {
        switch (event.getEventType()) {
            case CREATED:
                listeners.forEach(listener -> listener.onTimetableCreated(event));
                break;
            case UPDATED:
                listeners.forEach(listener -> listener.onTimetableUpdated(event));
                break;
            case DELETED:
                listeners.forEach(listener -> listener.onTimetableDeleted(event));
                break;
        }
    }

    /**
     * Create a new timetable slot with conflict detection
     * @param dto the timetable creation DTO
     * @return the created timetable response DTO
     * @throws TimeSlotConflictException if a time conflict is detected
     * @throws ResourceNotFoundException if subject not found
     */
    public TimetableResponseDTO createTimetableSlot(TimetableCreateDTO dto) {
        // Find the subject
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found",
                    "Subject",
                    dto.getSubjectId()
                ));

        // Create the new timetable slot
        Timetable timetable = new Timetable(
            subject,
            dto.getDay(),
            dto.getStartTime(),
            dto.getEndTime()
        );
        timetable.setLocation(dto.getLocation());
        timetable.setClassroom(dto.getClassroom());

        // Validate slot parameters
        ConflictValidator.validateTimetableSlot(timetable);

        // Check for conflicts with existing slots for this subject
        List<Timetable> existingSlots = timetableRepository.findBySubject(subject);
        ConflictValidator.validateNoConflict(timetable, existingSlots);

        // Save and notify
        Timetable saved = timetableRepository.save(timetable);
        notifyListeners(new TimetableEvent(TimetableEvent.TimetableEventType.CREATED, saved));

        return mapToResponseDTO(saved);
    }

    /**
     * Get all timetable slots for a subject
     */
    public List<TimetableResponseDTO> getTimetableForSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject not found",
                    "Subject",
                    subjectId
                ));

        List<Timetable> slots = timetableRepository.findBySubjectOrderByDayAscStartTimeAsc(subject);
        return slots.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get weekly timetable for a specific day
     */
    public List<TimetableResponseDTO> getTimetableForDay(DayOfWeek day) {
        List<Timetable> slots = timetableRepository.findByDay(day);
        return slots.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific timetable slot
     */
    public TimetableResponseDTO getTimetableSlot(Long id) {
        Timetable slot = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Timetable slot not found",
                    "Timetable",
                    id
                ));
        return mapToResponseDTO(slot);
    }

    /**
     * Update a timetable slot
     */
    public TimetableResponseDTO updateTimetableSlot(Long id, TimetableCreateDTO dto) {
        Timetable slot = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Timetable slot not found",
                    "Timetable",
                    id
                ));

        // Update fields
        slot.setDay(dto.getDay());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setLocation(dto.getLocation());
        slot.setClassroom(dto.getClassroom());

        // Validate slot parameters
        ConflictValidator.validateTimetableSlot(slot);

        // Check for conflicts (excluding current slot)
        List<Timetable> existingSlots = timetableRepository.findBySubject(slot.getSubject())
                .stream()
                .filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList());
        ConflictValidator.validateNoConflict(slot, existingSlots);

        // Save and notify
        Timetable updated = timetableRepository.save(slot);
        notifyListeners(new TimetableEvent(TimetableEvent.TimetableEventType.UPDATED, updated));

        return mapToResponseDTO(updated);
    }

    /**
     * Delete a timetable slot
     */
    public void deleteTimetableSlot(Long id) {
        Timetable slot = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Timetable slot not found",
                    "Timetable",
                    id
                ));

        timetableRepository.deleteById(id);
        notifyListeners(new TimetableEvent(TimetableEvent.TimetableEventType.DELETED, slot));
    }

    /**
     * Get all timetable slots
     */
    public List<TimetableResponseDTO> getAllTimetableSlots() {
        List<Timetable> slots = timetableRepository.findAll();
        return slots.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map entity to response DTO
     */
    private TimetableResponseDTO mapToResponseDTO(Timetable timetable) {
        TimetableResponseDTO dto = new TimetableResponseDTO();
        dto.setId(timetable.getId());
        dto.setSubjectId(timetable.getSubject().getId());
        dto.setSubjectName(timetable.getSubject().getName());
        dto.setSubjectCode(timetable.getSubject().getCode());
        dto.setDay(timetable.getDay());
        dto.setStartTime(timetable.getStartTime());
        dto.setEndTime(timetable.getEndTime());
        dto.setLocation(timetable.getLocation());
        dto.setClassroom(timetable.getClassroom());
        return dto;
    }
}
