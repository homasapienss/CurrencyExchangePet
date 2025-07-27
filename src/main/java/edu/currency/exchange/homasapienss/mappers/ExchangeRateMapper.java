package edu.currency.exchange.homasapienss.mappers;

import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;

public class ExchangeRateMapper {
    public ExchangeRateDTO toDTO(ExchangeRate exchangeRate) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setRate(exchangeRate.getRate());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        return exchangeRateDTO;
    }
    public ExchangeRate toEntity(ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(exchangeRateDTO.getRate());
        exchangeRate.setBaseCurrency(exchangeRateDTO.getBaseCurrency());
        exchangeRate.setTargetCurrency(exchangeRateDTO.getTargetCurrency());
        return exchangeRate;
    }
}
