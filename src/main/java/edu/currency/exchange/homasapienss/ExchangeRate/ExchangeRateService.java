package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.Currency.CurrencyService;
import edu.currency.exchange.homasapienss.exception.BadRequestException;
import edu.currency.exchange.homasapienss.exception.already_exists.ExchangeRateAlreadyExistsException;
import edu.currency.exchange.homasapienss.exception.not_found.ExchangeRateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepo exchangeRateRepo;
    private final CurrencyService currencyService;

//    @Transactional(readOnly = true)
//    public ExchangeRate getExchangeRateByIds(Long fromId, Long toId) {
//        return exchangeRateRepo.findByBaseCurrency_IdAndTargetCurrency_Id(fromId, toId)
//                .orElseThrow(ExchangeRateNotFoundException::new);
//    }

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

    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRateByCodePair(String codePair) {
        ValidatedCodePair validated = validateCodePair(codePair);
        return exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(validated.getFrom(), validated.getTo())
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRateByCodes(String from, String to) {
        return exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(from, to)
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Optional<ExchangeRate> findExchangeRateByCodes(String from, String to) {
        return exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(from, to);
    }

    @Transactional(readOnly = true)
    public boolean existsExchangeRateByCodes(String from, String to) {
        return exchangeRateRepo.existsByBaseCurrency_CodeAndTargetCurrency_Code(from, to);
    }

    @Transactional
    public ExchangeRate patchExchangeRate(String codePair, BigDecimal rate) {
        ValidatedCodePair validated = validateCodePair(codePair);
        ExchangeRate byCodes = exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(validated.getFrom(), validated.getTo())
                .orElseThrow(ExchangeRateNotFoundException::new);
        byCodes.setRate(rate);
        return byCodes;
    }

    private ValidatedCodePair validateCodePair(String codePair) {
        if ((codePair.length() != 6) || (!codePair.isBlank())) {
            throw new BadRequestException("Плохой ввод валютной пары");
        }
        String from = codePair.substring(0, 3);
        String to = codePair.substring(3, 6);
        return new ValidatedCodePair(from, to);
    }
}
