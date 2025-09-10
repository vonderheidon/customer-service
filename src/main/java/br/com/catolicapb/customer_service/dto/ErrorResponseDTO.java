package br.com.catolicapb.customer_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO {

    private String errorMessage;

    private LocalDateTime localDateTime;

    private Integer errorStatusCode;

    private String uri;
}
