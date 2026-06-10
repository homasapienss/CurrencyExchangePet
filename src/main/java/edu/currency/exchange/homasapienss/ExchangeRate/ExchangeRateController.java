package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if ((codePair.length() != 6) && (!codePair.isBlank()) ) {
            throw new BadRequestException("Плохой ввод валютной пары");
        }
        String from = codePair.substring(0, 3);
        String to = codePair.substring(3, 6);
        return ResponseEntity
                .ok(exchangeRateService.getExchangeRateByCodes(from, to));
    }

    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRate> createExchangeRate(@RequestBody ExchangeRateCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exchangeRateService.create(createRequest));
    }
}
