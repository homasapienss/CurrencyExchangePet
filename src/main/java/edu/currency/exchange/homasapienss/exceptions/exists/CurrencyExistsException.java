package edu.currency.exchange.homasapienss.exceptions.exists;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class CurrencyExistsException extends AlreadyExistsException{
    public CurrencyExistsException() {
        super(ErrorMessage.ALREADY_EXISTS);
    }
}
