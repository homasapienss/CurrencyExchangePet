package edu.currency.exchange.homasapienss.exception.already_exists;

public class ExchangeRateAlreadyExistsException extends AlreadyExistsException {
    public ExchangeRateAlreadyExistsException() {
        super("Валютный курс уже существует");
    }
}
