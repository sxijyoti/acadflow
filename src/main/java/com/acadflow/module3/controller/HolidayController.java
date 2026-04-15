package com.acadflow.module3.controller;

import com.acadflow.module3.dto.HolidayCreateDTO;
import com.acadflow.module3.dto.HolidayDTO;
import com.acadflow.module3.entity.Holiday;
import com.acadflow.module3.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Holiday management
 * Endpoints for viewing and managing holidays
 */
@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    /**
     * GET /api/v1/holidays
     * Get all holidays
     * @return List of all holidays
     */
    @GetMapping
    public ResponseEntity<List<HolidayDTO>> getAllHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays();
        List<HolidayDTO> holidayDTOs = holidays.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(holidayDTOs);
    }

    /**
     * GET /api/v1/holidays/range
     * Get holidays within a date range
     * @param startDate the start date (ISO format: YYYY-MM-DD)
     * @param endDate the end date (ISO format: YYYY-MM-DD)
     * @return List of holidays in the range
     */
    @GetMapping("/range")
    public ResponseEntity<List<HolidayDTO>> getHolidaysByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<Holiday> holidays = holidayService.getHolidaysInRange(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
        List<HolidayDTO> holidayDTOs = holidays.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(holidayDTOs);
    }

    /**
     * GET /api/v1/holidays/type/{type}
     * Get holidays by type
     * @param type the holiday type
     * @return List of holidays of that type
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<HolidayDTO>> getHolidaysByType(@PathVariable String type) {
        List<Holiday> holidays = holidayService.getHolidaysInRange(
                LocalDate.of(1900, 1, 1),
                LocalDate.of(2100, 12, 31)
        ).stream()
                .filter(h -> h.getType().equals(type))
                .collect(Collectors.toList());
        List<HolidayDTO> holidayDTOs = holidays.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(holidayDTOs);
    }

    /**
     * GET /api/v1/holidays/{id}
     * Get a specific holiday by ID
     * @param id the holiday ID
     * @return the holiday
     */
    @GetMapping("/{id}")
    public ResponseEntity<HolidayDTO> getHolidayById(@PathVariable Long id) {
        Holiday holiday = holidayService.getHolidayById(id);
        if (holiday == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(holiday));
    }

    /**
     * POST /api/v1/holidays
     * Create a new holiday
     * @param holidayCreateDTO the holiday data
     * @return the created holiday
     */
    @PostMapping
    public ResponseEntity<HolidayDTO> createHoliday(@RequestBody HolidayCreateDTO holidayCreateDTO) {
        Holiday holiday = new Holiday();
        holiday.setName(holidayCreateDTO.getName());
        holiday.setDate(holidayCreateDTO.getDate());
        holiday.setType(holidayCreateDTO.getType());

        Holiday createdHoliday = holidayService.createHoliday(holiday);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdHoliday));
    }

    /**
     * PUT /api/v1/holidays/{id}
     * Update an existing holiday
     * @param id the holiday ID
     * @param holidayCreateDTO the updated holiday data
     * @return the updated holiday
     */
    @PutMapping("/{id}")
    public ResponseEntity<HolidayDTO> updateHoliday(@PathVariable Long id, @RequestBody HolidayCreateDTO holidayCreateDTO) {
        Holiday existingHoliday = holidayService.getHolidayById(id);
        if (existingHoliday == null) {
            return ResponseEntity.notFound().build();
        }

        existingHoliday.setName(holidayCreateDTO.getName());
        existingHoliday.setDate(holidayCreateDTO.getDate());
        existingHoliday.setType(holidayCreateDTO.getType());

        Holiday updatedHoliday = holidayService.updateHoliday(id, existingHoliday);
        return ResponseEntity.ok(convertToDTO(updatedHoliday));
    }

    /**
     * DELETE /api/v1/holidays/{id}
     * Delete a holiday
     * @param id the holiday ID
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        Holiday holiday = holidayService.getHolidayById(id);
        if (holiday == null) {
            return ResponseEntity.notFound().build();
        }
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/v1/holidays/check/{date}
     * Check if a specific date is a holiday
     * @param date the date to check (ISO format: YYYY-MM-DD)
     * @return true if the date is a holiday
     */
    @GetMapping("/check/{date}")
    public ResponseEntity<Boolean> isHoliday(@PathVariable String date) {
        boolean result = holidayService.isHoliday(LocalDate.parse(date));
        return ResponseEntity.ok(result);
    }

    /**
     * Helper method to convert Holiday entity to HolidayDTO
     */
    private HolidayDTO convertToDTO(Holiday holiday) {
        HolidayDTO dto = new HolidayDTO();
        dto.setId(holiday.getId());
        dto.setName(holiday.getName());
        dto.setDate(holiday.getDate());
        dto.setType(holiday.getType());
        return dto;
    }
}
