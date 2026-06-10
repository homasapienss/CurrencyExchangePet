package edu.currency.exchange.homasapienss.exception.already_exists;

public class CurrencyAlreadyExistsException extends AlreadyExistsException {
    public CurrencyAlreadyExistsException() {
        super("Валюта уже существует");
    }
}
