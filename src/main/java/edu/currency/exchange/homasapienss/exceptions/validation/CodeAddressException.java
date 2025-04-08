package edu.currency.exchange.homasapienss.exceptions.validation;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class CodeAddressException extends ValidationException{
    public CodeAddressException() {
        super(ErrorMessage.NO_CODE_URL);
    }
}
