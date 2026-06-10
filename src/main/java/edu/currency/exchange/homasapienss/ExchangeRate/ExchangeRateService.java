package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.Currency.CurrencyService;
import edu.currency.exchange.homasapienss.exception.already_exists.ExchangeRateAlreadyExistsException;
import edu.currency.exchange.homasapienss.exception.not_found.ExchangeRateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepo exchangeRateRepo;
    private final CurrencyService currencyService;

    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRateByIds(Long fromId, Long toId) {
        return exchangeRateRepo.findByBaseCurrency_IdAndTargetCurrency_Id(fromId, toId)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRateByCodes(String fromCode, String toCode) {
        return exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(fromCode, toCode)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<ExchangeRate> getAll() {
        return exchangeRateRepo.findAll();
    }

    @Transactional
    public ExchangeRate create(ExchangeRateCreateRequest createRequest) {
        boolean byCodes = exchangeRateRepo.existsByBaseCurrency_CodeAndTargetCurrency_Code(createRequest.getBaseCurrencyCode(), createRequest.getTargetCurrencyCode());
        if (byCodes) {
            throw new ExchangeRateAlreadyExistsException();
        }
        return exchangeRateRepo.save(ExchangeRate.builder()
                .baseCurrency(currencyService.getByCode(createRequest.getBaseCurrencyCode()))
                .targetCurrency(currencyService.getByCode(createRequest.getTargetCurrencyCode()))
                .rate(createRequest.getRate())
                .build());
    }
}
