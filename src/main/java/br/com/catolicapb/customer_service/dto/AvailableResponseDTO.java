package br.com.catolicapb.customer_service.dto;

import br.com.catolicapb.customer_service.enums.ScheduleShift;

public record AvailableResponseDTO(
        ScheduleShift shift,
        int availableNumber) {
}
