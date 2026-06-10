package edu.currency.exchange.homasapienss.exception;

import edu.currency.exchange.homasapienss.exception.already_exists.AlreadyExistsException;
import edu.currency.exchange.homasapienss.exception.not_found.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleApplicationException(ApplicationException ex) {
        return ErrorMessage.create(ex);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleAlreadyExistsException(AlreadyExistsException ex) {
        return ErrorMessage.create(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException ex) {
        return ErrorMessage.create(ex);
    }
}
