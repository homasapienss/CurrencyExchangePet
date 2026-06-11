package edu.currency.exchange.homasapienss.exchange;

import edu.currency.exchange.homasapienss.Currency.Currency;
import edu.currency.exchange.homasapienss.Currency.CurrencyService;
import edu.currency.exchange.homasapienss.ExchangeRate.ExchangeRate;
import edu.currency.exchange.homasapienss.ExchangeRate.ExchangeRateService;
import edu.currency.exchange.homasapienss.exception.ApplicationException;
import edu.currency.exchange.homasapienss.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private static final String CROSS_CURRENCY_CODE = "USD";
    private static final MathContext RATE_MATH_CONTEXT = MathContext.DECIMAL64;

    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    @Transactional(readOnly = true)
    public ExchangeDto exchange(String from, String to, BigDecimal amount) {
        if (!(currencyService.existsByCode(from) && currencyService.existsByCode(to)))
            throw new BadRequestException("плохой ввод валютных кодов");
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BadRequestException("введите корректное число для конвертации");

        Currency baseCurrency = currencyService.getByCode(from);
        Currency targetCurrency = currencyService.getByCode(to);
        BigDecimal rate = calculateRate(from, to);
        BigDecimal convertedAmount = rate.multiply(amount);

        return ExchangeDto.builder()
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(rate)
                .amount(amount)
                .convertedAmount(convertedAmount)
                .build();
    }

    private BigDecimal calculateRate(String from, String to) {
        if (from.equals(to)) {
            return BigDecimal.ONE;
        }

        return exchangeRateService.findExchangeRateByCodes(from, to)
                .map(ExchangeRate::getRate)
                .or(() -> exchangeRateService.findExchangeRateByCodes(to, from)
                        .map(exchangeRate -> BigDecimal.ONE.divide(exchangeRate.getRate(), RATE_MATH_CONTEXT)))
                .or(() -> calculateCrossRate(from, to))
                .orElseThrow(() -> new ApplicationException("обменный курс не найден"));
    }

    private Optional<BigDecimal> calculateCrossRate(String from, String to) {
        var usdRateFrom = exchangeRateService.findExchangeRateByCodes(CROSS_CURRENCY_CODE, from);
        var usdRateTo = exchangeRateService.findExchangeRateByCodes(CROSS_CURRENCY_CODE, to);

        if (usdRateFrom.isEmpty() || usdRateTo.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal fromRate = usdRateFrom.get().getRate();
        BigDecimal toRate = usdRateTo.get().getRate();
        return Optional.of(toRate.divide(fromRate, RATE_MATH_CONTEXT));
    }
}
