package com.acadflow.module3.controller;

import com.acadflow.module3.dto.EventCreateDTO;
import com.acadflow.module3.dto.EventDTO;
import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;
import com.acadflow.module3.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Calendar management
 * Endpoints for viewing and managing events in the unified calendar
 */
@RestController
@RequestMapping("/api/v1/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    /**
     * GET /api/v1/calendar
     * Get all events in the calendar
     * @return List of all events
     */
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = calendarService.getEvents();
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/subject/{subjectId}
     * Get events for a specific subject
     * @param subjectId the subject ID
     * @return List of events for that subject
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<EventDTO>> getEventsBySubject(@PathVariable Long subjectId) {
        List<Event> events = calendarService.getEventsBySubject(subjectId);
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/range
     * Get events within a date range
     * @param startDate the start date (ISO format)
     * @param endDate the end date (ISO format)
     * @return List of events in the range
     */
    @GetMapping("/range")
    public ResponseEntity<List<EventDTO>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Event> events = calendarService.getEventsByDateRange(startDate, endDate);
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/type/{type}
     * Get events filtered by type
     * @param type the event type (ASSIGNMENT, EXAM, HOLIDAY)
     * @return List of events of that type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<EventDTO>> getEventsByType(@PathVariable EventType type) {
        List<Event> events = calendarService.getEventsByType(type);
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/filtered
     * Get events filtered by type and date range
     * @param type the event type
     * @param startDate the start date (ISO format)
     * @param endDate the end date (ISO format)
     * @return List of filtered events
     */
    @GetMapping("/filtered")
    public ResponseEntity<List<EventDTO>> getFilteredEvents(
            @RequestParam EventType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Event> events = calendarService.getFilteredEvents(type, startDate, endDate);
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/important
     * Get all important events (highlighted)
     * @return List of important events
     */
    @GetMapping("/important")
    public ResponseEntity<List<EventDTO>> getImportantEvents() {
        List<Event> events = calendarService.getImportantEvents();
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }

    /**
     * GET /api/v1/calendar/{id}
     * Get a specific event by ID
     * @param id the event ID
     * @return the event
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Event event = calendarService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(event));
    }

    /**
     * POST /api/v1/calendar
     * Create a new event
     * @param eventCreateDTO the event data
     * @return the created event
     */
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreateDTO eventCreateDTO) {
        Event event = new Event();
        event.setTitle(eventCreateDTO.getTitle());
        event.setType(eventCreateDTO.getType());
        event.setDate(eventCreateDTO.getDate());
        event.setDescription(eventCreateDTO.getDescription());
        event.setIsImportant(eventCreateDTO.getIsImportant() != null ? eventCreateDTO.getIsImportant() : false);

        Event createdEvent = calendarService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdEvent));
    }

    /**
     * PUT /api/v1/calendar/{id}
     * Update an existing event
     * @param id the event ID
     * @param eventCreateDTO the updated event data
     * @return the updated event
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventCreateDTO eventCreateDTO) {
        Event existingEvent = calendarService.getEventById(id);
        if (existingEvent == null) {
            return ResponseEntity.notFound().build();
        }

        existingEvent.setTitle(eventCreateDTO.getTitle());
        existingEvent.setType(eventCreateDTO.getType());
        existingEvent.setDate(eventCreateDTO.getDate());
        existingEvent.setDescription(eventCreateDTO.getDescription());
        existingEvent.setIsImportant(eventCreateDTO.getIsImportant());

        Event updatedEvent = calendarService.updateEvent(id, existingEvent);
        return ResponseEntity.ok(convertToDTO(updatedEvent));
    }

    /**
     * DELETE /api/v1/calendar/{id}
     * Delete an event
     * @param id the event ID
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        Event event = calendarService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        calendarService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Helper method to convert Event entity to EventDTO
     */
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setType(event.getType());
        dto.setDate(event.getDate());
        dto.setDescription(event.getDescription());
        dto.setIsImportant(event.getIsImportant());
        if (event.getSubject() != null) {
            dto.setSubjectId(event.getSubject().getId());
            dto.setSubjectName(event.getSubject().getName());
        }
        return dto;
    }
}
