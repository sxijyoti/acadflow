package com.acadflow.module4.service;

import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;
import com.acadflow.module3.repository.EventRepository;
import com.acadflow.module4.event.ExamEvent;
import com.acadflow.module4.event.ExamEventListener;
import com.acadflow.module4.event.TimetableEvent;
import com.acadflow.module4.event.TimetableEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that publishes Module 4 events to Module 3 (Calendar)
 * Implements Observer Pattern for cross-module integration
 * Dependency Inversion Principle: Depends on interfaces, not concrete implementations
 */
@Service
@Transactional
public class EventPublisher implements ExamEventListener, TimetableEventListener {

    private final EventRepository eventRepository;

    public EventPublisher(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ========== EXAM EVENT HANDLERS ==========

    /**
     * When an exam is created in Module 4, push it to Module 3 events
     */
    @Override
    public void onExamCreated(ExamEvent event) {
        // Create a corresponding event in Module 3
        Event calendarEvent = new Event(
            "Exam: " + event.getExam().getSubject().getName(),
            EventType.EXAM,
            event.getExam().getDate(),
            event.getExam().getSubject()
        );

        // Add exam details to description
        StringBuilder description = new StringBuilder();
        description.append("Type: ").append(event.getExam().getType()).append("\n");
        if (event.getExam().getLocation() != null) {
            description.append("Location: ").append(event.getExam().getLocation()).append("\n");
        }
        if (event.getExam().getDuration() != null) {
            description.append("Duration: ").append(event.getExam().getDuration()).append(" minutes\n");
        }
        if (event.getExam().getMaxMarks() != null) {
            description.append("Max Marks: ").append(event.getExam().getMaxMarks()).append("\n");
        }
        if (event.getExam().getInstructions() != null) {
            description.append("Instructions: ").append(event.getExam().getInstructions());
        }

        calendarEvent.setDescription(description.toString());
        calendarEvent.setIsImportant(true); // Exams are marked as important

        eventRepository.save(calendarEvent);
    }

    /**
     * When an exam is updated in Module 4, update it in Module 3
     */
    @Override
    public void onExamUpdated(ExamEvent event) {
        // Find and delete old event from Module 3
        // Note: In a real scenario, you might want to find the exact event and update it
        // This is a simplified approach that removes the old event and creates a new one
        
        // For now, we're just creating the notification
        // The actual sync logic would be more sophisticated
    }

    /**
     * When an exam is deleted in Module 4, remove it from Module 3
     */
    @Override
    public void onExamDeleted(ExamEvent event) {
        // Find and delete corresponding event from Module 3
        // This would typically involve finding events by exam details
        // For now, this serves as a hook point for future implementation
    }

    // ========== TIMETABLE EVENT HANDLERS ==========

    /**
     * When a timetable slot is created in Module 4
     * Note: Timetable slots are not typically added to calendar, 
     * but this serves as a hook point for future features like recurring events
     */
    @Override
    public void onTimetableCreated(TimetableEvent event) {
        // Future implementation: Could add recurring class events to calendar
        // Currently, timetable is only for viewing, not for calendar events
    }

    /**
     * When a timetable slot is updated in Module 4
     */
    @Override
    public void onTimetableUpdated(TimetableEvent event) {
        // Future implementation: Update corresponding calendar entries
    }

    /**
     * When a timetable slot is deleted in Module 4
     */
    @Override
    public void onTimetableDeleted(TimetableEvent event) {
        // Future implementation: Remove corresponding calendar entries
    }
}
