package edu.currency.exchange.homasapienss.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidatedCodePair {
    private String from;
    private String to;
}
