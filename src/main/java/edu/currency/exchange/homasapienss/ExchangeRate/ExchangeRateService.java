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
    public List<ExchangeRateDto> getAll() {
        return exchangeRateRepo.findAll().stream()
                .map(ExchangeRateDto::from)
                .toList();
    }

    @Transactional
    public ExchangeRateDto create(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        boolean byCodes = exchangeRateRepo.existsByBaseCurrency_CodeAndTargetCurrency_Code(baseCurrencyCode, targetCurrencyCode);
        if (byCodes) {
            throw new ExchangeRateAlreadyExistsException();
        }
        ExchangeRate exchangeRate = exchangeRateRepo.save(ExchangeRate.builder()
                .baseCurrency(currencyService.getByCode(baseCurrencyCode))
                .targetCurrency(currencyService.getByCode(targetCurrencyCode))
                .rate(rate)
                .build());
        return ExchangeRateDto.from(exchangeRate);
    }

    @Transactional(readOnly = true)
    public ExchangeRateDto getExchangeRateByCodePair(String codePair) {
        ValidatedCodePair validated = validateCodePair(codePair);
        ExchangeRate exchangeRate = exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(validated.getFrom(), validated.getTo())
                .orElseThrow(ExchangeRateNotFoundException::new);
        return ExchangeRateDto.from(exchangeRate);
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
    public ExchangeRateDto patchExchangeRate(String codePair, BigDecimal rate) {
        ValidatedCodePair validated = validateCodePair(codePair);
        ExchangeRate byCodes = exchangeRateRepo.findByBaseCurrency_CodeAndTargetCurrency_Code(validated.getFrom(), validated.getTo())
                .orElseThrow(ExchangeRateNotFoundException::new);
        byCodes.setRate(rate);
        return ExchangeRateDto.from(byCodes);
    }

    private ValidatedCodePair validateCodePair(String codePair) {
        if ((codePair.length() != 6) || codePair.isBlank()) {
            throw new BadRequestException("Плохой ввод валютной пары");
        }
        String from = codePair.substring(0, 3);
        String to = codePair.substring(3, 6);
        return new ValidatedCodePair(from, to);
    }
}
