package edu.currency.exchange.homasapienss.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateCreateRequest {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal rate;
}
