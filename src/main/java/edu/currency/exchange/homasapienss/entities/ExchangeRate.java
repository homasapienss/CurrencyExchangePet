package edu.currency.exchange.homasapienss.entities;

import java.math.BigDecimal;

public class ExchangeRate {
    private int id;
    private int baseCurrency;
    private int targetCurrency;
    private BigDecimal rate;

    public ExchangeRate(int baseCurrency, int targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeRate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.rate =rate;
    }
}
