package edu.currency.exchange.homasapienss.exceptions.validation;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class InvalidDataException extends ValidationException{
    public InvalidDataException() {
        super(ErrorMessage.INVALID_DATA);
    }
}
