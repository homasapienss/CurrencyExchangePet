package edu.currency.exchange.homasapienss.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyCreateRequest {
    private String name;
    private String code;
    private String sign;
}
