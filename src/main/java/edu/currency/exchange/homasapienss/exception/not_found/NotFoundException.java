package edu.currency.exchange.homasapienss.exception.not_found;

import edu.currency.exchange.homasapienss.exception.ApplicationException;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message);
    }
}
