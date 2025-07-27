package edu.currency.exchange.homasapienss.dto;

import java.math.BigDecimal;

public class ExchangeRateDTO {
    private int baseCurrency;
    private int targetCurrency;
    private BigDecimal rate;


    public ExchangeRateDTO() {
    }

    public int getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(int baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public int getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(int targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
