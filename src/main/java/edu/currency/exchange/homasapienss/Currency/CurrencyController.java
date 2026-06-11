package edu.currency.exchange.homasapienss.Currency;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("/currencies") // показать все валюты
    public List<Currency> getCurrencies() {
        return currencyService.getAll();
    }

    @GetMapping("/currency/{code}") // показать валюту по коду
    public ResponseEntity<Currency> getCurrencies(@PathVariable("code") String code) {
        return ResponseEntity
                .ok(currencyService.getByCode(code));
    }

    @PostMapping("/currencies") // добавить новую валюту
    public ResponseEntity<Currency> createCurrency(@RequestBody CurrencyCreateRequest currencyCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(currencyService.create(currencyCreateRequest));
    }
}
