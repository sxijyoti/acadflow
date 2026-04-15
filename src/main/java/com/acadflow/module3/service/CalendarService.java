package com.acadflow.module3.service;

import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.repository.AssignmentRepository;
import com.acadflow.module3.adapter.AssignmentEventAdapter;
import com.acadflow.module3.adapter.EventProvider;
import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;
import com.acadflow.module3.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing calendar events
 * Implements EventProvider interface for providing academic events
 * Aggregates assignments from Module 2 into unified event format
 */
@Service
public class CalendarService implements EventProvider {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * Get all events (both stored events and converted assignments)
     * @return List of all events
     */
    @Override
    public List<Event> getEvents() {
        List<Event> events = eventRepository.findAll();
        // Aggregate assignments as events
        List<Assignment> assignments = assignmentRepository.findAll();
        List<Event> assignmentEvents = assignments.stream()
                .map(AssignmentEventAdapter::convertAssignmentToEvent)
                .collect(Collectors.toList());
        events.addAll(assignmentEvents);
        return events;
    }

    /**
     * Get events for a specific subject
     * @param subjectId the subject ID
     * @return List of events for that subject
     */
    @Override
    public List<Event> getEventsBySubject(Long subjectId) {
        List<Event> events = eventRepository.findBySubjectId(subjectId);
        // Also get assignments for this subject
        List<Assignment> assignments = assignmentRepository.findBySubjectId(subjectId);
        List<Event> assignmentEvents = assignments.stream()
                .map(AssignmentEventAdapter::convertAssignmentToEvent)
                .collect(Collectors.toList());
        events.addAll(assignmentEvents);
        return events;
    }

    /**
     * Get events within a specific date range
     * @param startDate the start date
     * @param endDate the end date
     * @return List of events in the range
     */
    public List<Event> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findByDateBetween(startDate, endDate);
        // Also get assignments within date range
        List<Assignment> assignments = assignmentRepository.findByDeadlineBetween(startDate, endDate);
        List<Event> assignmentEvents = assignments.stream()
                .map(AssignmentEventAdapter::convertAssignmentToEvent)
                .collect(Collectors.toList());
        events.addAll(assignmentEvents);
        return events;
    }

    /**
     * Get events filtered by type
     * @param type the event type
     * @return List of events of that type
     */
    public List<Event> getEventsByType(EventType type) {
        if (type == EventType.ASSIGNMENT) {
            // Return assignments converted to events
            List<Assignment> assignments = assignmentRepository.findAll();
            return assignments.stream()
                    .map(AssignmentEventAdapter::convertAssignmentToEvent)
                    .collect(Collectors.toList());
        }
        return eventRepository.findByType(type);
    }

    /**
     * Get filtered events by type and date range
     * @param type the event type
     * @param startDate the start date
     * @param endDate the end date
     * @return List of filtered events
     */
    public List<Event> getFilteredEvents(EventType type, LocalDateTime startDate, LocalDateTime endDate) {
        if (type == EventType.ASSIGNMENT) {
            List<Assignment> assignments = assignmentRepository.findByDeadlineBetween(startDate, endDate);
            return assignments.stream()
                    .map(AssignmentEventAdapter::convertAssignmentToEvent)
                    .collect(Collectors.toList());
        }
        return eventRepository.findByTypeAndDateBetween(type, startDate, endDate);
    }

    /**
     * Get all important events (marked for highlighting)
     * @return List of important events
     */
    public List<Event> getImportantEvents() {
        return eventRepository.findByIsImportantTrue();
    }

    /**
     * Create a new event
     * @param event the event to create
     * @return the created event
     */
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Update an existing event
     * @param id the event ID
     * @param event the updated event
     * @return the updated event
     */
    public Event updateEvent(Long id, Event event) {
        event.setId(id);
        return eventRepository.save(event);
    }

    /**
     * Delete an event
     * @param id the event ID
     */
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Get event by ID
     * @param id the event ID
     * @return the event if found
     */
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}
