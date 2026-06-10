package edu.currency.exchange.homasapienss.exception.not_found;

public class CurrencyNotFoundException extends NotFoundException{
    public CurrencyNotFoundException() {
        super("валюта не найдена");
    }
}
