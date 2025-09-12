package br.com.catolicapb.customer_service.dto;

import br.com.catolicapb.customer_service.enums.ScheduleShift;
import br.com.catolicapb.customer_service.enums.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SchedulingDetailResponseDTO {

    private Long id;

    private Long vetId;

    private Long petId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateSchedule;

    private ScheduleShift scheduleShift;

    private ScheduleStatus scheduleStatus;
}