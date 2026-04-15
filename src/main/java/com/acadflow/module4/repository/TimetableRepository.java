package com.acadflow.module4.repository;

import com.acadflow.module4.entity.Timetable;
import com.acadflow.module4.entity.DayOfWeek;
import com.acadflow.module1.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    /**
     * Find all timetable slots for a specific subject
     */
    List<Timetable> findBySubject(Subject subject);

    /**
     * Find all timetable slots for a specific subject on a specific day
     */
    List<Timetable> findBySubjectAndDay(Subject subject, DayOfWeek day);

    /**
     * Find all timetable slots on a specific day across all subjects
     */
    List<Timetable> findByDay(DayOfWeek day);

    /**
     * Find all timetable slots for a subject sorted by day
     */
    List<Timetable> findBySubjectOrderByDayAscStartTimeAsc(Subject subject);
}
