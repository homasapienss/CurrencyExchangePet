package edu.currency.exchange.homasapienss.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeDto> exchange(@RequestParam String from,
                                                @RequestParam String to,
                                                @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(exchangeService.exchange(from, to, amount));
    }
}
