package br.com.catolicapb.customer_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SchedulingNotFoundException extends RuntimeException {

    public SchedulingNotFoundException(String msg) {
        super(msg);
    }
}