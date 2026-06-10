package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.exception.not_found.ExchangeRateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepo exchangeRateRepo;

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
}
