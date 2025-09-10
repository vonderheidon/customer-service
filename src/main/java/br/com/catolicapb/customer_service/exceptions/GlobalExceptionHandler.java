package br.com.catolicapb.customer_service.exceptions;

import br.com.catolicapb.customer_service.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LimitAvailableException.class)
    public ResponseEntity<ErrorResponseDTO> handlerLimitException(LimitAvailableException ex, WebRequest request) {
        var errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(ex.getMessage())
                .uri(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .errorStatusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
