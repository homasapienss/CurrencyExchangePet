package edu.currency.exchange.homasapienss.Currency;

import edu.currency.exchange.homasapienss.exception.already_exists.CurrencyAlreadyExistsException;
import edu.currency.exchange.homasapienss.exception.not_found.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Currency> getAll() {
        return currencyRepo.findAll();
    }

    @Transactional
    public Currency create(CurrencyCreateRequest currencyCreateRequest) {
        boolean byCode = currencyRepo.existsByCode(currencyCreateRequest.getCode());
        if (byCode) {
            throw new CurrencyAlreadyExistsException();
        }
        return currencyRepo.save(Currency.builder()
                .code(currencyCreateRequest.getCode())
                .name(currencyCreateRequest.getName())
                .sign(currencyCreateRequest.getSign())
                .build());
    }
}
