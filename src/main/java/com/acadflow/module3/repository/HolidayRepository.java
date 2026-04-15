package com.acadflow.module3.repository;

import com.acadflow.module3.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    /**
     * Find holidays by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of holidays in the range
     */
    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find holidays by type
     * @param type the holiday type
     * @return list of holidays of that type
     */
    List<Holiday> findByType(String type);

    /**
     * Find holiday by name
     * @param name the holiday name
     * @return list of holidays with that name
     */
    List<Holiday> findByName(String name);
}
