package edu.currency.exchange.homasapienss.exceptions.notfound;

import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

public class ExchangeRateNotFoundException extends NotFoundException{
    public ExchangeRateNotFoundException() {
        super(ErrorMessage.NO_CODE_PAIR_DB);
    }
}
