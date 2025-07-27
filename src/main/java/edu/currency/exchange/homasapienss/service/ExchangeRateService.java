package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.exists.CodePairExistsException;
import edu.currency.exchange.homasapienss.exceptions.notfound.CurrencyNotFoundException;
import edu.currency.exchange.homasapienss.exceptions.notfound.ExchangeRateNotFoundException;
import edu.currency.exchange.homasapienss.mappers.ExchangeRateMapper;

import java.sql.SQLException;
import java.util.List;

public class ExchangeRateService {

    ExchangeRateDAO exchangeRateDAO;
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();
    private final CurrencyDAO currencyDAO;

    public ExchangeRateService(ExchangeRateDAO exchangeRateDAO, CurrencyDAO currencyDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
        this.currencyDAO = currencyDAO;
    }

    public int getBaseCurrencyId(String code) {
        try {
            Currency currency = currencyDAO.getByCode(code)
                    .orElseThrow(CurrencyNotFoundException::new);
            return currency.getId();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public int getTargetCurrencyId(String code) {
        try {
            Currency currency = currencyDAO.getByCode(code)
                    .orElseThrow(CurrencyNotFoundException::new);
            return currency.getId();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public ExchangeRateDTO getByCodePair(String baseCurrency, String targetCurrency) {
        try {
            Currency currencyFrom = currencyDAO.getByCode(baseCurrency)
                    .orElseThrow(()  ->new ApplicationException(ErrorMessage.NO_CODE_PAIR_DB));
            Currency currencyTo = currencyDAO.getByCode(targetCurrency)
                    .orElseThrow(() -> new ApplicationException(ErrorMessage.NO_CODE_PAIR_DB));
            return exchangeRateDAO.getByIdPair(currencyFrom.getId(), currencyTo.getId())
                    .map(exchangeRateMapper::toDTO)
                    .orElseThrow(() -> new ApplicationException(ErrorMessage.NO_CODE_PAIR_DB));
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public ExchangeRateDTO getById(int id) {
        try {
            return exchangeRateDAO.getById(id)
                    .map(exchangeRateMapper::toDTO)
                    .orElseThrow(ExchangeRateNotFoundException::new);
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
