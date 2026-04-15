package com.acadflow.module4.controller;

import com.acadflow.module4.dto.TimetableCreateDTO;
import com.acadflow.module4.dto.TimetableResponseDTO;
import com.acadflow.module4.entity.DayOfWeek;
import com.acadflow.module4.exception.TimeSlotConflictException;
import com.acadflow.module4.service.TimetableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Timetable Management
 * Endpoints:
 * - POST /api/v1/timetable - Create timetable slot
 * - GET /api/v1/timetable - Get all timetable slots
 * - GET /api/v1/timetable/{id} - Get specific timetable slot
 * - GET /api/v1/timetable/subject/{subjectId} - Get timetable for subject
 * - GET /api/v1/timetable/day/{day} - Get timetable for specific day
 * - PUT /api/v1/timetable/{id} - Update timetable slot
 * - DELETE /api/v1/timetable/{id} - Delete timetable slot
 */
@RestController
@RequestMapping("/api/v1/timetable")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    /**
     * Create a new timetable slot
     * @param dto the timetable creation DTO
     * @return the created timetable response
     * @throws TimeSlotConflictException if time slot conflict detected
     */
    @PostMapping
    public ResponseEntity<?> createTimetableSlot(@RequestBody TimetableCreateDTO dto) {
        try {
            TimetableResponseDTO response = timetableService.createTimetableSlot(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TimeSlotConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage() + " - " + e.getConflictDetails());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get all timetable slots
     */
    @GetMapping
    public ResponseEntity<List<TimetableResponseDTO>> getAllTimetableSlots() {
        List<TimetableResponseDTO> slots = timetableService.getAllTimetableSlots();
        return ResponseEntity.ok(slots);
    }

    /**
     * Get timetable slot by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTimetableSlot(@PathVariable Long id) {
        try {
            TimetableResponseDTO slot = timetableService.getTimetableSlot(id);
            return ResponseEntity.ok(slot);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get timetable for a specific subject
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getTimetableForSubject(@PathVariable Long subjectId) {
        try {
            List<TimetableResponseDTO> slots = timetableService.getTimetableForSubject(subjectId);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Get weekly timetable for a specific day
     */
    @GetMapping("/day/{day}")
    public ResponseEntity<?> getTimetableForDay(@PathVariable DayOfWeek day) {
        try {
            List<TimetableResponseDTO> slots = timetableService.getTimetableForDay(day);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Update a timetable slot with conflict detection
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimetableSlot(
            @PathVariable Long id,
            @RequestBody TimetableCreateDTO dto) {
        try {
            TimetableResponseDTO updated = timetableService.updateTimetableSlot(id, dto);
            return ResponseEntity.ok(updated);
        } catch (TimeSlotConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage() + " - " + e.getConflictDetails());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Delete a timetable slot
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimetableSlot(@PathVariable Long id) {
        try {
            timetableService.deleteTimetableSlot(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }
}
