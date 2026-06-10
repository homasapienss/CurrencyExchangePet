package edu.currency.exchange.homasapienss.ExchangeRate;

import edu.currency.exchange.homasapienss.Currency.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "exchange_rates",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_stock_shop_product", columnNames = {"base_currency_id", "target_currency_id"})
        })
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_currency_id", nullable = false)
    private Currency baseCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_currency_id", nullable = false)
    private Currency targetCurrency;
    @Column(precision = 38, scale = 36, nullable = false)
    private BigDecimal rate;
}
