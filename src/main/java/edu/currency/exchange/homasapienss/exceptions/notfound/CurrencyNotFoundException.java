package edu.currency.exchange.homasapienss.exceptions.notfound;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class CurrencyNotFoundException extends NotFoundException{
    public CurrencyNotFoundException() {
        super(ErrorMessage.NO_CODE_DB);
    }
}
