package edu.currency.exchange.homasapienss.exceptions.exists;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException(ErrorMessage message) {
        super(message);
    }
}
