package br.com.catolicapb.customer_service.domain;

import br.com.catolicapb.customer_service.enums.ScheduleShift;
import br.com.catolicapb.customer_service.enums.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scheduling extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vetId;

    private Long petId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateSchedule;

    @Enumerated(EnumType.STRING)
    private ScheduleShift scheduleShift;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;

    @OneToOne(mappedBy = "scheduling")
    private MedicalCare medicalCare;
}
