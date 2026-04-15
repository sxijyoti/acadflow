package com.acadflow.ui.services;

import com.acadflow.ui.dto.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Sample Data Provider for UI Testing
 * Generates mock data when API calls fail or during development
 */
public class SampleDataProvider {

    // Sample Subjects
    public static List<SubjectDTO> getSampleSubjects() {
        List<SubjectDTO> subjects = new ArrayList<>();
        subjects.add(new SubjectDTO(1L, "CS101", "Data Structures", "Learn fundamental data structures", "Dr. Smith", 3));
        subjects.add(new SubjectDTO(2L, "CS102", "Algorithms", "Algorithm design and analysis", "Dr. Johnson", 4));
        subjects.add(new SubjectDTO(3L, "CS201", "Database Systems", "Database design and SQL", "Dr. Williams", 3));
        subjects.add(new SubjectDTO(4L, "CS202", "Web Development", "Full stack web development", "Dr. Brown", 3));
        subjects.add(new SubjectDTO(5L, "MATH101", "Calculus", "Differential and Integral Calculus", "Prof. Davis", 4));
        
        // Set additional fields
        subjects.get(0).setTotalClasses(30);
        subjects.get(0).setAttendedClasses(28);
        subjects.get(1).setTotalClasses(30);
        subjects.get(1).setAttendedClasses(25);
        
        return subjects;
    }

    public static List<SubjectDTO> getSampleEnrolledSubjects() {
        List<SubjectDTO> enrolled = new ArrayList<>();
        enrolled.add(new SubjectDTO(1L, "CS101", "Data Structures", "Learn fundamental data structures", "Dr. Smith", 3));
        enrolled.add(new SubjectDTO(2L, "CS102", "Algorithms", "Algorithm design and analysis", "Dr. Johnson", 4));
        enrolled.add(new SubjectDTO(5L, "MATH101", "Calculus", "Differential and Integral Calculus", "Prof. Davis", 4));
        
        // Set additional fields
        enrolled.get(0).setTotalClasses(30);
        enrolled.get(0).setAttendedClasses(28);
        enrolled.get(1).setTotalClasses(30);
        enrolled.get(1).setAttendedClasses(25);
        enrolled.get(2).setTotalClasses(30);
        enrolled.get(2).setAttendedClasses(20);
        
        return enrolled;
    }

    // Sample Assignments
    public static List<AssignmentDTO> getSampleAssignments() {
        List<AssignmentDTO> assignments = new ArrayList<>();
        
        assignments.add(createAssignment(1L, "Array Implementation", "CS101", LocalDate.now().plusDays(5), "PENDING"));
        assignments.add(createAssignment(2L, "Sorting Algorithms", "CS102", LocalDate.now().plusDays(10), "PENDING"));
        assignments.add(createAssignment(3L, "Database Schema Design", "CS201", LocalDate.now().minusDays(2), "SUBMITTED"));
        assignments.add(createAssignment(4L, "REST API Design", "CS202", LocalDate.now().plusDays(7), "PENDING"));
        assignments.add(createAssignment(5L, "Integration Submission", "CS102", LocalDate.now().minusDays(5), "LATE"));
        
        return assignments;
    }

    private static AssignmentDTO createAssignment(Long id, String title, String subject, LocalDate dueDate, String status) {
        AssignmentDTO a = new AssignmentDTO(id, title, subject, dueDate, status);
        a.setSubjectName(subject.equals("CS101") ? "Data Structures" : 
                        subject.equals("CS102") ? "Algorithms" :
                        subject.equals("CS201") ? "Database Systems" : "Web Development");
        a.setDescription("Complete the assignment and submit the solution");
        return a;
    }

    // Sample Exams
    public static List<ExamDTO> getSampleExams() {
        List<ExamDTO> exams = new ArrayList<>();
        
        exams.add(createExam(1L, "CS101", "Data Structures", LocalDate.now().plusDays(15), "MIDTERM", "Room 101"));
        exams.add(createExam(2L, "CS102", "Algorithms", LocalDate.now().plusDays(20), "MIDTERM", "Room 102"));
        exams.add(createExam(3L, "CS201", "Database Systems", LocalDate.now().plusDays(25), "FINAL", "Room 201"));
        exams.add(createExam(4L, "MATH101", "Calculus", LocalDate.now().plusDays(30), "FINAL", "Room 301"));
        
        return exams;
    }

    private static ExamDTO createExam(Long id, String code, String name, LocalDate date, String type, String location) {
        ExamDTO exam = new ExamDTO(id, code, name, date, type, location);
        exam.setStartTime(LocalTime.of(10, 0));
        exam.setEndTime(LocalTime.of(12, 0));
        exam.setTotalMarks(100);
        return exam;
    }

    // Sample Timetable
    public static List<TimetableSlotDTO> getSampleTimetable() {
        List<TimetableSlotDTO> timetable = new ArrayList<>();
        
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        
        for (int i = 0; i < days.length; i++) {
            timetable.add(new TimetableSlotDTO(days[i], LocalTime.of(9, 0), LocalTime.of(10, 0), 
                                               "CS101", "Data Structures"));
            timetable.add(new TimetableSlotDTO(days[i], LocalTime.of(10, 30), LocalTime.of(11, 30), 
                                               "CS102", "Algorithms"));
            timetable.add(new TimetableSlotDTO(days[i], LocalTime.of(14, 0), LocalTime.of(15, 30), 
                                               "CS201", "Database Systems"));
        }
        
        return timetable;
    }

    // Sample Holidays
    public static List<HolidayDTO> getSampleHolidays() {
        List<HolidayDTO> holidays = new ArrayList<>();
        
        holidays.add(new HolidayDTO(1L, "New Year's Day", LocalDate.of(2026, 1, 1), "National Holiday"));
        holidays.add(new HolidayDTO(2L, "Independence Day", LocalDate.of(2026, 8, 15), "National Holiday"));
        holidays.add(new HolidayDTO(3L, "Dussehra", LocalDate.of(2026, 10, 12), "Religious Holiday"));
        holidays.add(new HolidayDTO(4L, "Diwali", LocalDate.of(2026, 11, 8), "Religious Holiday"));
        holidays.add(new HolidayDTO(5L, "Winter Break", LocalDate.of(2026, 12, 20), "Academic Holiday"));
        
        return holidays;
    }

    // Sample Resources
    public static List<ResourceDTO> getSampleResources() {
        List<ResourceDTO> resources = new ArrayList<>();
        
        resources.add(new ResourceDTO(1L, "CS101", "DS Fundamentals", "PDF", "https://example.com/ds.pdf"));
        resources.add(new ResourceDTO(2L, "CS101", "Data Structures Video Lecture 1", "VIDEO", "https://youtube.com/watch?v=123"));
        resources.add(new ResourceDTO(3L, "CS102", "Algorithm Design Patterns", "PDF", "https://example.com/algo.pdf"));
        resources.add(new ResourceDTO(4L, "CS102", "Sorting Algorithms Tutorial", "LINK", "https://tutorialspoint.com/sorting"));
        resources.add(new ResourceDTO(5L, "CS201", "SQL Tutorial", "VIDEO", "https://youtube.com/watch?v=456"));
        
        return resources;
    }

    // Sample User
    public static UserDTO getSampleUser() {
        UserDTO user = new UserDTO(1L, "John Doe", "john.doe@university.edu", "STUDENT");
        user.setPhone("+1-234-567-8900");
        user.setDepartment("Computer Science");
        user.setSemester("6");
        user.setPhotoURL("https://api.example.com/photos/user1.jpg");
        return user;
    }

    // Sample Events for Calendar
    public static List<EventDTO> getSampleCalendarEvents() {
        List<EventDTO> events = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        
        // Upcoming assignments
        events.add(new EventDTO("Array Implementation", "ASSIGNMENT", today.plusDays(5).toString(), "#FFD700"));
        events.add(new EventDTO("Database Schema", "ASSIGNMENT", today.plusDays(10).toString(), "#FFD700"));
        
        // Upcoming exams
        events.add(new EventDTO("Data Structures Midterm", "EXAM", today.plusDays(15).toString(), "#FF6B6B"));
        events.add(new EventDTO("Algorithms Midterm", "EXAM", today.plusDays(20).toString(), "#FF6B6B"));
        
        // Holidays
        events.add(new EventDTO("Dussehra", "HOLIDAY", today.plusDays(45).toString(), "#90EE90"));
        events.add(new EventDTO("Diwali", "HOLIDAY", today.plusDays(60).toString(), "#90EE90"));
        
        return events;
    }
}
