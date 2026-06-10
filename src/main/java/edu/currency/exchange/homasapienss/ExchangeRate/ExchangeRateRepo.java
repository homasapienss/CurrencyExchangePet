package edu.currency.exchange.homasapienss.ExchangeRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepo extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByBaseCurrency_IdAndTargetCurrency_Id(Long baseCurrencyId, Long targetCurrencyId);

    Optional<ExchangeRate> findByBaseCurrency_CodeAndTargetCurrency_Code(String baseCurrencyCode, String targetCurrencyCode);

    boolean existsByBaseCurrency_CodeAndTargetCurrency_Code(String baseCurrencyCode, String targetCurrencyCode);
}
