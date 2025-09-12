package br.com.catolicapb.customer_service.dto;

import br.com.catolicapb.customer_service.enums.ScheduleShift;
import br.com.catolicapb.customer_service.enums.ScheduleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SchedeUpdateRequestDTO {

    private Long vetId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent(message = "Data inválida")
    private LocalDate dateSchedule;

    private ScheduleShift scheduleShift;

    private ScheduleStatus scheduleStatus;
}