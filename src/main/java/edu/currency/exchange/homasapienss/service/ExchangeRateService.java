package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateCurrenciesDTO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.exists.CodePairExistsException;
import edu.currency.exchange.homasapienss.exceptions.notfound.ExchangeRateNotFoundException;
import edu.currency.exchange.homasapienss.mappers.ExchangeRateMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();
    private final CurrencyService currencyService = new CurrencyService();

    public int getCurrencyIdByCode(String code) {
        return currencyService.getIdByCode(code);
    }

    public ExchangeRateDTO getByCodePair(String baseCurrency, String targetCurrency) {
        int baseId = currencyService.getIdByCode(baseCurrency);
        int targetId = currencyService.getIdByCode(targetCurrency);
        try {
            return exchangeRateDAO.getByIdPair(baseId, targetId)
                    .map(exchangeRateMapper::toDTO)
                    .orElseThrow(() -> new ApplicationException(ErrorMessage.NO_CODE_PAIR_DB));
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public ExchangeRateCurrenciesDTO getById(int id) {
        try {
            ExchangeRateCurrenciesDTO fullRate = new ExchangeRateCurrenciesDTO();
            ExchangeRate exchangeRate = exchangeRateDAO.getById(id)
                    .orElseThrow(ExchangeRateNotFoundException::new);
            fullRate.setRate(exchangeRate.getRate());
            fullRate.setTargetCurrency(currencyService.getById(exchangeRate.getTargetCurrency()));
            fullRate.setBaseCurrency(currencyService.getById(exchangeRate.getBaseCurrency()));
            return fullRate;
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public List<ExchangeRateDTO> getAll() {
        try {
            return exchangeRateDAO.getAll()
                    .stream().map(exchangeRateMapper::toDTO)
                    .toList();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public List<ExchangeRateCurrenciesDTO> getAllDTO() {
        try {
            List <ExchangeRate> exchangeRates = exchangeRateDAO.getAll();
            List <ExchangeRateCurrenciesDTO> fullExchangeRates = new ArrayList<>();
            for (ExchangeRate exRate : exchangeRates) {
                ExchangeRateCurrenciesDTO fullExchangeRate = new ExchangeRateCurrenciesDTO();
                fullExchangeRate.setBaseCurrency(currencyService
                                                         .getById(exRate.getBaseCurrency()));
                fullExchangeRate.setTargetCurrency(currencyService
                                                           .getById(exRate.getTargetCurrency()));
                fullExchangeRate.setRate(exRate.getRate());
                fullExchangeRates.add(fullExchangeRate);
            }
            return fullExchangeRates;
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void save(ExchangeRateDTO exchangeRateDTO) {
        try {
            ExchangeRate exchangeRate = exchangeRateMapper.toEntity(exchangeRateDTO);
            Integer savedRows = exchangeRateDAO.save(exchangeRate);
            if (savedRows == 0) {
                throw new CodePairExistsException();
            }
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void update(ExchangeRateDTO exchangeRateDTO) {
        try {
            ExchangeRate exchangeRate = exchangeRateMapper.toEntity(exchangeRateDTO);
            Integer updatedRows = exchangeRateDAO.update(exchangeRate);
            if (updatedRows == 0) {
                throw new ExchangeRateNotFoundException();
            }
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void delete(Integer id) {
        try {
            Integer deletedRows = exchangeRateDAO.delete(id);
            if (deletedRows == 0) {
                throw new ExchangeRateNotFoundException();
            }
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }
}
