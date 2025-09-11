package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeDTO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService {
    CurrencyService currencyService = new CurrencyService();
    ExchangeRateService exchangeRateService = new ExchangeRateService();

    public ExchangeDTO doExchange(String from, String to, String amountString) {
        ExchangeDTO exchangeDTO = new ExchangeDTO();
        double amount = Double.parseDouble(amountString);

        exchangeDTO.setBaseCurrency(currencyService.getByCode(from));
        exchangeDTO.setTargetCurrency(currencyService.getByCode(to));
        exchangeDTO.setAmount(BigDecimal.valueOf(amount));

        if (from.equals(to)) {
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }

        try {
            ExchangeRateDTO exchangeRateDTO = exchangeRateService.getByCodePair(from, to);
            exchangeDTO.setRate(exchangeRateDTO.getRate());
            exchangeDTO.setConvertedAmount(amountToDecimal(amount, exchangeDTO.getRate()));
            return exchangeDTO;
        } catch (ApplicationException e1) {
            try {
                ExchangeRateDTO exchangeRateDTO = exchangeRateService.getByCodePair(to, from);
                exchangeDTO.setRate(BigDecimal.ONE.divide(exchangeRateDTO.getRate(), 4,
                                                          RoundingMode.HALF_UP));
                exchangeDTO.setConvertedAmount(amountToDecimal(amount, exchangeDTO.getRate()));
                return exchangeDTO;
            } catch (ApplicationException e2) {
                try {
                    BigDecimal usdFrom = exchangeRateService.getByCodePair("USD", from).getRate();
                    BigDecimal usdTo = exchangeRateService.getByCodePair("USD", to).getRate();
                    BigDecimal crossRate = usdTo.divide(usdFrom, 2, RoundingMode.HALF_UP);
                    exchangeDTO.setRate(crossRate);
                    exchangeDTO.setConvertedAmount(amountToDecimal(amount, exchangeDTO.getRate()));
                    return exchangeDTO;
                }catch (ApplicationException e3){
                    throw new ApplicationException(ErrorMessage.ERROR);
                }
            }
        }
    }

    private BigDecimal amountToDecimal(double amount, BigDecimal rate) {
        return BigDecimal.valueOf(amount).multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
