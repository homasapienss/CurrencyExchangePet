package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;

import java.util.List;
import java.util.Optional;

public class CurrencyService {
    CurrencyDAO currencyDAO = new CurrencyDAO();

    public Optional<Currency> getById(int id) {
        System.out.println("currency service get by id");
        return currencyDAO.getById(id);
    }

    public Optional<Currency> getByCode(String code) {
        System.out.println("currency service get by code");
        return currencyDAO.getByCode(code);
    }

    public List<Currency> getAll() throws ApplicationException {
        System.out.println("currency service get all");
        return currencyDAO.getAll();
    }

    public Currency create(Currency entity) {
        System.out.println("currency service create");
        return currencyDAO.create(entity);
    }

    public Currency update(Currency entity) {
        System.out.println("currency service update");
        return currencyDAO.update(entity);
    }

    public void delete(int id) {
        System.out.println("currency service delete");
        currencyDAO.delete(id);
    }
}
