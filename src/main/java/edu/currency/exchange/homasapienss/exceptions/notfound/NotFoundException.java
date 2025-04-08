package edu.currency.exchange.homasapienss.exceptions.notfound;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class NotFoundException extends ApplicationException {
    public NotFoundException(ErrorMessage message) {
        super(message);
    }
}
