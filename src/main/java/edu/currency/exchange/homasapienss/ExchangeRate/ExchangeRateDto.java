package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.Currency.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ExchangeRateDto {
    private Long id;
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    private BigDecimal rate;

    public static ExchangeRateDto from(ExchangeRate exchangeRate) {
        return ExchangeRateDto.builder()
                .id(exchangeRate.getId())
                .baseCurrency(CurrencyDto.from(exchangeRate.getBaseCurrency()))
                .targetCurrency(CurrencyDto.from(exchangeRate.getTargetCurrency()))
                .rate(exchangeRate.getRate())
                .build();
    }

    @Getter
    @Builder
    public static class CurrencyDto {
        private Long id;
        private String code;
        private String name;
        private String sign;

        public static CurrencyDto from(Currency currency) {
            return CurrencyDto.builder()
                    .id(currency.getId())
                    .code(currency.getCode())
                    .name(currency.getName())
                    .sign(currency.getSign())
                    .build();
        }
    }
}
