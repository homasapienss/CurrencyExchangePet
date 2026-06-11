package edu.currency.exchange.homasapienss.ExchangeRate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/exchangeRates") // получить все валютные курсы
    public ResponseEntity<List<ExchangeRateDto>> getAllExchangeRates() {
        return ResponseEntity
                .ok(exchangeRateService.getAll());
    }

    @GetMapping("/exchangeRate/{codePair}") // получить ввалютный курс по валютной паре
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@PathVariable("codePair") String codePair) {
        return ResponseEntity
                .ok(exchangeRateService.getExchangeRateByCodePair(codePair));
    }

    @PatchMapping("/exchangeRate/{codePair}") //изменить обменный курс валют
    public ResponseEntity<ExchangeRateDto> patchExchangeRate(@PathVariable("codePair") String codePair,
                                                             @RequestParam BigDecimal rate) {
        return ResponseEntity
                .ok(exchangeRateService.patchExchangeRate(
                        codePair,
                        rate)
                );
    }

    @PostMapping("/exchangeRates") // создать новый валютный курс
    public ResponseEntity<ExchangeRateDto> createExchangeRate(@RequestParam("baseCurrencyCode") String base,
                                                              @RequestParam("targetCurrencyCode") String target,
                                                              @RequestParam BigDecimal rate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exchangeRateService.create(base, target, rate));
    }
}
