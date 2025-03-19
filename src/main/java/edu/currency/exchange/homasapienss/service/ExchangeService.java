package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeService {
    CurrencyService currencyService = new CurrencyService();
    ExchangeRateService exchangeRateService = new ExchangeRateService();
    CurrencyDAO currencyDAO = new CurrencyDAO();
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();

    public ExchangeDTO doExchange(String from, String to, String amountString) {
        ExchangeDTO exchangeDTO = new ExchangeDTO();
        double answer = 0.0d;
        double amount = Double.parseDouble(amountString);
        exchangeDTO.setBaseCurrency(currencyService.getByCode(from).get());
        exchangeDTO.setTargetCurrency(currencyService.getByCode(to).get());
        exchangeDTO.setAmount(BigDecimal.valueOf(amount));
        if (currencyService.getByCode(from).isEmpty() || currencyService.getByCode(to).isEmpty() ||
            from.equals(to)) {
            throw new RuntimeException();
        } else if (exchangeRateDAO.getByCodePair(from, to).isPresent()) {
            ExchangeRate exchangeRate = exchangeRateService.getByCodePair(from, to);
            BigDecimal rate = exchangeRate.getRate();
            exchangeDTO.setRate(rate);
            exchangeDTO.setConvertedAmount(amountToDecimal(amount, rate));
            return exchangeDTO;
        } else if (exchangeRateDAO.getByCodePair(to, from).isPresent()) {
            ExchangeRate exchangeRate = exchangeRateService.getByCodePair(to, from);
            BigDecimal rate = exchangeRate.getRate();
            exchangeDTO.setRate(rate);
            exchangeDTO.setConvertedAmount(amountToDecimal(amount, rate));

            return exchangeDTO;
        }
        return null;
    }

    private BigDecimal amountToDecimal(double amount, BigDecimal rate) {
        return BigDecimal.valueOf(amount).multiply(rate);
    }
}
