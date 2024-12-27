package br.com.agrotis.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT)
public class AlreadyUsedException extends ResponseStatusException {

    public AlreadyUsedException() {
        super(CONFLICT);
    }

    public AlreadyUsedException(String errorMessage) {
        super(CONFLICT, errorMessage);
    }

}
