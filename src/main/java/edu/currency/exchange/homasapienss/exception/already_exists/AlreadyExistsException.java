package edu.currency.exchange.homasapienss.exception.already_exists;

import edu.currency.exchange.homasapienss.exception.ApplicationException;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
