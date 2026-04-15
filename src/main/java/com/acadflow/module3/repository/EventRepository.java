package com.acadflow.module3.repository;

import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find events by type
     * @param type the event type
     * @return list of events of that type
     */
    List<Event> findByType(EventType type);

    /**
     * Find events for a specific subject
     * @param subjectId the subject ID
     * @return list of events for that subject
     */
    List<Event> findBySubjectId(Long subjectId);

    /**
     * Find events within a date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of events in the range
     */
    List<Event> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find events by type and date range
     * @param type the event type
     * @param startDate the start date
     * @param endDate the end date
     * @return list of events of that type in the range
     */
    List<Event> findByTypeAndDateBetween(EventType type, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find important events
     * @return list of important events
     */
    List<Event> findByIsImportantTrue();
}
