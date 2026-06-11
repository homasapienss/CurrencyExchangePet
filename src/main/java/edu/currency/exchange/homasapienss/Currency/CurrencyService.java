package edu.currency.exchange.homasapienss.Currency;

import edu.currency.exchange.homasapienss.exception.BadRequestException;
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

//    @Transactional(readOnly = true)
//    public Currency getById(Long id) {
//        return currencyRepo.findById(id)
//                .orElseThrow(CurrencyNotFoundException::new);
//    }

    @Transactional(readOnly = true)
    public Currency getByCode(String code) {
        String validatedCode = validateCode(code);
        return currencyRepo.findByCode(validatedCode)
                .orElseThrow(CurrencyNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Currency> getAll() {
        return currencyRepo.findAll();
    }

    @Transactional
    public Currency create(String code, String name, String sign) {
        boolean byCode = currencyRepo.existsByCode(code);
        if (byCode) {
            throw new CurrencyAlreadyExistsException();
        }
        return currencyRepo.save(Currency.builder()
                .code(code)
                .name(name)
                .sign(sign)
                .build());
    }

    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return currencyRepo.existsByCode(code);
    }

    private String validateCode(String code) {
        if (code == null || code.isBlank() || code.length() != 3) {
            throw new BadRequestException("Валюта введена не правлильно");
        }
        return code;
    }
}
