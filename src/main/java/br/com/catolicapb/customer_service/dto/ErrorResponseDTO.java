package br.com.catolicapb.customer_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponseDTO {

    private String errorMessage;

    private LocalDateTime localDateTime;

    private Integer errorStatusCode;

    private String uri;

    private List<String> errors;
}
