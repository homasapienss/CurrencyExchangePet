package edu.currency.exchange.homasapienss.mappers;

import edu.currency.exchange.homasapienss.dto.CurrencyDTO;
import edu.currency.exchange.homasapienss.entities.Currency;

public class CurrencyMapper {
    public CurrencyDTO toDTO(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCode(currency.getCode());
        currencyDTO.setName(currency.getName());
        currencyDTO.setSign(currency.getSign());
        return currencyDTO;
    }
    public Currency toEntity(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setCode(currencyDTO.getCode());
        currency.setName(currencyDTO.getName());
        currency.setSign(currencyDTO.getSign());
        return currency;
    }
}
