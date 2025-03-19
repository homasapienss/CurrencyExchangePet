package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateService {

    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    CurrencyDAO currencyDAO = new CurrencyDAO();

    public ExchangeRate getByCodePair(String baseCurrency, String targetCurrency) {
        if (currencyDAO.getByCode(baseCurrency).isEmpty() ||
            currencyDAO.getByCode(targetCurrency).isEmpty()) {
            throw new RuntimeException();
        } else if (exchangeRateDAO.getByCodePair(baseCurrency, targetCurrency).isEmpty()) {
            throw new RuntimeException();
        }
        return exchangeRateDAO.getByCodePair(baseCurrency, targetCurrency).get();
    }

    public ExchangeRate getById(int id) {
        if (exchangeRateDAO.getById(id).isEmpty()) {
            throw new RuntimeException();
        }
        return exchangeRateDAO.getById(id).get();
    }

    public List<ExchangeRate> getAll() {
        return exchangeRateDAO.getAll();
    }

    public ExchangeRate create(ExchangeRate entity) {
        return exchangeRateDAO.create(entity);
    }

    public ExchangeRate update(ExchangeRate entity) {
        return exchangeRateDAO.update(entity);
    }

    public void delete(int id) {
        exchangeRateDAO.delete(id);
    }

    public ExchangeRateDTO convertToExchangeRateDTO(ExchangeRate entity) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setId(entity.getId());
        exchangeRateDTO.setBaseCurrency(currencyDAO.getById(entity.getBaseCurrency()).get());
        exchangeRateDTO.setTargetCurrency(currencyDAO.getById(entity.getTargetCurrency()).get());
        exchangeRateDTO.setRate(entity.getRate());
        return exchangeRateDTO;
    }

    public List<ExchangeRateDTO> convertToListExchangeRateDTO(List<ExchangeRate> exchangeRates) {
        List<ExchangeRateDTO> exchangeRateDTOS = new ArrayList<>();
        for (int i = 0; i < exchangeRates.size(); i++) {
            ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
            exchangeRateDTO.setId(exchangeRates.get(i).getId());
            exchangeRateDTO.setBaseCurrency(
                    currencyDAO.getById(exchangeRates.get(i).getBaseCurrency()).get());
            exchangeRateDTO.setTargetCurrency(
                    currencyDAO.getById(exchangeRates.get(i).getTargetCurrency()).get());
            exchangeRateDTO.setRate(exchangeRates.get(i).getRate());
            exchangeRateDTOS.add(exchangeRateDTO);
        }

        return exchangeRateDTOS;
    }

}
