package br.com.catolicapb.customer_service.repository;

import br.com.catolicapb.customer_service.domain.Scheduling;
import br.com.catolicapb.customer_service.enums.ScheduleShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    @Query("""
            select count(s) from Scheduling s
            where s.dateSchedule = :dateSchedule
            and s.scheduleShift  in :shift
            and s.vetId = :vetId
            """)
    Long verifyScheduleAvailable(
            LocalDate dateSchedule,
            ScheduleShift shift,
            Long vetId);
}
