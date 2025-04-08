package edu.currency.exchange.homasapienss.exceptions.validation;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class ValidationException extends ApplicationException {

    public ValidationException(ErrorMessage message) {
        super(message);
    }
}
