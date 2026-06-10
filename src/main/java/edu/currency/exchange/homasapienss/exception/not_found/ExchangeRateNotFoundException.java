package edu.currency.exchange.homasapienss.exception.not_found;

public class ExchangeRateNotFoundException extends NotFoundException {
    public ExchangeRateNotFoundException() {
        super("валютный курс не найден");
    }
}
