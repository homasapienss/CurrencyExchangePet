package edu.currency.exchange.homasapienss.Currency;

import edu.currency.exchange.homasapienss.exception.not_found.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo currencyRepo;

    @Transactional(readOnly = true)
    public Currency getByCode(String code) {
        return currencyRepo.findByCode(code)
                .orElseThrow(CurrencyNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Currency getById(Long id) {
        return currencyRepo.findById(id)
                .orElseThrow(CurrencyNotFoundException::new);
    }
}
