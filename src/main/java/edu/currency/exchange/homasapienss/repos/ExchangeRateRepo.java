package edu.currency.exchange.homasapienss.repos;

import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepo extends JpaRepository<ExchangeRate, Long> {
}
