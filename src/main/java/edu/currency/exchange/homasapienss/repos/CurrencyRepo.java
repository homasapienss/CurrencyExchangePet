package edu.currency.exchange.homasapienss.repos;

import edu.currency.exchange.homasapienss.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long> {
}
