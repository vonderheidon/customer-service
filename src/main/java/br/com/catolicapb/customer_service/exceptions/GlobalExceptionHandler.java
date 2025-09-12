package br.com.catolicapb.customer_service.exceptions;

import br.com.catolicapb.customer_service.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

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

    @ExceptionHandler(SchedulingNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerResourceNotFoundException(SchedulingNotFoundException ex, WebRequest request) {
        var errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(ex.getMessage())
                .uri(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .errorStatusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        var errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage("Erro de validação")
                .errors(errors)
                .uri(request.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .errorStatusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDTO);
    }


}
