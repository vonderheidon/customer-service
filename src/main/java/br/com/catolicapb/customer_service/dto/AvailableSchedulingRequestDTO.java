package br.com.catolicapb.customer_service.dto;

import br.com.catolicapb.customer_service.enums.ScheduleShift;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableSchedulingRequestDTO {

    private Long vetId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent(message = "Data inválida")
    private LocalDate dateSchedule;

    private ScheduleShift shift;
}
