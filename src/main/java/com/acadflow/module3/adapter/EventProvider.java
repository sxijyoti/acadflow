package com.acadflow.module3.adapter;

import com.acadflow.module3.entity.Event;
import java.util.List;

/**
 * ISP: Separate interface for providing events from academic sources
 * (assignments, exams, etc.)
 */
public interface EventProvider {
    /**
     * Get all events from this provider
     * @return List of events
     */
    List<Event> getEvents();

    /**
     * Get events for a specific subject
     * @param subjectId the subject ID
     * @return List of events for that subject
     */
    List<Event> getEventsBySubject(Long subjectId);
}
