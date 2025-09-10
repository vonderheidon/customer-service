package br.com.catolicapb.customer_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {

    private String responseMessage;

    private Integer httpCodeStatus;
}
