package br.com.catolicapb.customer_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LimitAvailableException extends RuntimeException {

    public LimitAvailableException(String msg) {
        super(msg);
    }
}
