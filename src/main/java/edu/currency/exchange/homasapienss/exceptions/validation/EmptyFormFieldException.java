package edu.currency.exchange.homasapienss.exceptions.validation;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class EmptyFormFieldException extends ValidationException{
    public EmptyFormFieldException() {
        super(ErrorMessage.NO_DATA_FIELD);
    }
}
