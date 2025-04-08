package edu.currency.exchange.homasapienss.exceptions.exists;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class CodePairExistsException extends AlreadyExistsException{
    public CodePairExistsException() {
        super(ErrorMessage.ALREADY_EXISTS);
    }
}
