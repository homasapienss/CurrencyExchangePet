package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.ValidatedCodePair;
import edu.currency.exchange.homasapienss.exception.BadRequestException;
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

    @GetMapping("/exchangeRates")
    public ResponseEntity<List<ExchangeRate>> getAllExchangeRates() {
        return ResponseEntity
                .ok(exchangeRateService.getAll());
    }

    @GetMapping("/exchangeRate/{code-pair}")
    public ResponseEntity<ExchangeRate> getExchangeRate(@PathVariable("code-pair") String codePair) {
        return ResponseEntity
                .ok(exchangeRateService.getExchangeRateByCodePair(codePair));
    }

    @PatchMapping("/exchangeRate/{code-pair}") //изменить обменный курс валют
    public ResponseEntity<ExchangeRate> patchExchangeRate(@PathVariable("code-pair") String codePair,
                                                          @RequestParam BigDecimal rate) {
        return ResponseEntity
                .ok(exchangeRateService.patchExchangeRate(
                        codePair,
                        rate)
                );
    }

    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRate> createExchangeRate(@RequestBody ExchangeRateCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exchangeRateService.create(createRequest));
    }
}
