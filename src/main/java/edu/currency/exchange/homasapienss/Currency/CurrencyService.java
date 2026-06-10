package edu.currency.exchange.homasapienss.Currency;

import edu.currency.exchange.homasapienss.exception.not_found.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo currencyRepo;
    public Currency getByCode(String code) {
        return currencyRepo.findByCode(code)
                .orElseThrow(CurrencyNotFoundException::new);
    }

    public Currency getById(Long id) {
        return currencyRepo.findById(id)
                .orElseThrow(CurrencyNotFoundException::new);
    }
}
