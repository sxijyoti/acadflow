package com.acadflow.module3.adapter;

import com.acadflow.module2.entity.Assignment;
import com.acadflow.module3.entity.Event;
import com.acadflow.module3.entity.EventType;

/**
 * ADAPTER PATTERN: Converts Assignment (from Module 2) to Event
 * Adapts Assignment interface to unified Event format
 */
public class AssignmentEventAdapter {

    /**
     * Converts a Module 2 Assignment to a Module 3 Event
     * @param assignment the assignment to convert
     * @return an Event representing the assignment deadline
     */
    public static Event convertAssignmentToEvent(Assignment assignment) {
        Event event = new Event();
        event.setTitle("Assignment: " + assignment.getTitle());
        event.setType(EventType.ASSIGNMENT);
        event.setDate(assignment.getDeadline());
        event.setSubject(assignment.getSubject());
        event.setDescription(assignment.getDescription());
        event.setIsImportant(true); // Assignments are always marked as important
        return event;
    }
}
