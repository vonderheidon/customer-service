package br.com.catolicapb.customer_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulingResponseDTO {

    private Long vetId;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateSchedule;

    private List<AvailableResponseDTO> availableResponseDTO;
}
